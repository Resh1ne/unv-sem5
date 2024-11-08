package by.bsuir.pbz2.data.dao.impl;

import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.dao.ExhibitionHallDao;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.data.entity.ExhibitionParticipantsAndArtworks;
import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ExhibitionDaoImpl implements ExhibitionDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "INSERT INTO exhibitions " +
            "(name, hall_id, type_id, start_date, end_date) " +
            "VALUES (?, ?, (SELECT id FROM exhibition_types et WHERE et.exhibition_type = ?), ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT e.id, e.name, e.hall_id, et.exhibition_type, e.start_date, e.end_date " +
            "FROM exhibitions e " +
            "JOIN exhibition_types et ON e.type_id = et.id " +
            "WHERE e.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT e.id, e.name, e.hall_id, et.exhibition_type, e.start_date, e.end_date " +
            "FROM exhibitions e " +
            "JOIN exhibition_types et ON e.type_id = et.id ";

    private static final String FIND_PARTICIPANTS_ARTWORKS_BY_EXHIBITION_ID = "SELECT " +
            "exhibition_name, exhibition_start_date, exhibition_end_date, artwork_title, execution_type, artist_name, artist_age, creation_date " +
            "FROM get_exhibition_participants_and_artworks(?)";
    private static final String UPDATE_QUERY = "UPDATE exhibitions " +
            "SET " +
            "name = ?, " +
            "hall_id = ?, " +
            "type_id = (SELECT id FROM exhibition_types et WHERE et.exhibition_type = ?), " +
            "start_date = ?, " +
            "end_date = ? " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM exhibitions WHERE id = ?";


    @Override
    public Exhibition create(Exhibition entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getHallId().getId());
            statement.setString(3, entity.getType().toString());
            statement.setDate(4, Date.valueOf(entity.getStartDate()));
            statement.setDate(5, Date.valueOf(entity.getEndDate()));
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                return findById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create exhibition: " + entity);
    }

    @Override
    public Exhibition findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<ExhibitionParticipantsAndArtworks> findParticipantsArtworksByExhibitionId(Long id) {
        List<ExhibitionParticipantsAndArtworks> exhibitionParticipantsAndArtworks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_PARTICIPANTS_ARTWORKS_BY_EXHIBITION_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ExhibitionParticipantsAndArtworks exhibition = new ExhibitionParticipantsAndArtworks();
                exhibition.setExhibitionName(resultSet.getString("exhibition_name"));
                exhibition.setExhibitionStartDate(resultSet.getDate("exhibition_start_date").toLocalDate());
                exhibition.setExhibitionEndDate(resultSet.getDate("exhibition_end_date").toLocalDate());
                exhibition.setArtworkTitle(resultSet.getString("artwork_title"));
                exhibition.setExecutionType(ExecutionType.valueOf(resultSet.getString("execution_type")));
                exhibition.setArtistName(resultSet.getString("artist_name"));
                exhibition.setArtistAge(resultSet.getInt("artist_age"));
                exhibition.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
                exhibitionParticipantsAndArtworks.add(exhibition);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitionParticipantsAndArtworks;
    }

    private Exhibition mapRow(ResultSet resultSet) throws SQLException {
        Exhibition exhibition = new Exhibition();
        exhibition.setId(resultSet.getLong("id"));
        exhibition.setName(resultSet.getString("name"));
        Long hallId = resultSet.getLong("hall_id");
        ExhibitionHallDao exhibitionHallDao = new ExhibitionHallDaoImpl(this.dataSource);
        exhibition.setHallId(exhibitionHallDao.findById(hallId));
        exhibition.setType(ExhibitionType.valueOf(resultSet.getString("exhibition_type")));
        exhibition.setStartDate(resultSet.getDate("start_date").toLocalDate());
        exhibition.setEndDate(resultSet.getDate("end_date").toLocalDate());
        return exhibition;
    }

    @Override
    public List<Exhibition> findAll() {
        List<Exhibition> exhibitions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Exhibition exhibition = mapRow(resultSet);
                exhibitions.add(exhibition);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitions;
    }

    @Override
    public Exhibition update(Exhibition entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getHallId().getId());
            statement.setString(3, entity.getType().toString());
            statement.setDate(4, Date.valueOf(entity.getStartDate()));
            statement.setDate(5, Date.valueOf(entity.getEndDate()));
            statement.setLong(6, entity.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return findById(entity.getId());
            } else {
                throw new RuntimeException("Failed to update exhibition. No rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
