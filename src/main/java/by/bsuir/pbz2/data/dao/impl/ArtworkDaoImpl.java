package by.bsuir.pbz2.data.dao.impl;

import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.dao.ArtistDao;
import by.bsuir.pbz2.data.dao.ArtworkDao;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ArtworkDaoImpl implements ArtworkDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "INSERT INTO artworks " +
            "(title, execution_id, creation_date, height, width, volume, artist_id) " +
            "VALUES (?, (SELECT id FROM execution_types et WHERE et.execution = ?), ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT art.id, art.title, et.execution, art.creation_date, art.height, art.width, art.volume, art.artist_id " +
            "FROM artworks art " +
            "JOIN execution_types et ON art.execution_id = et.id " +
            "WHERE art.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT art.id, art.title, et.execution, art.creation_date, art.height, art.width, art.volume, art.artist_id " +
            "FROM artworks art " +
            "JOIN execution_types et ON art.execution_id = et.id ";
    private static final String UPDATE_QUERY = "UPDATE artworks " +
            "SET " +
            "title = ?, " +
            "execution_id = (SELECT id FROM execution_types et WHERE et.execution = ?), " +
            "creation_date = ?, " +
            "height = ?, " +
            "width = ?, " +
            "volume = ?, " +
            "artist_id = ? " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM artworks WHERE id = ?";

    @Override
    public Artwork create(Artwork entity) {
        log.info("Creating artwork: {}", entity);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getExecutionType().toString());
            statement.setDate(3, Date.valueOf(entity.getCreationDate()));
            setNullBigDecimal(4, entity.getHeight(), statement);
            setNullBigDecimal(5, entity.getWidth(), statement);
            setNullBigDecimal(6, entity.getVolume(), statement);
            statement.setLong(7, entity.getArtistId().getId());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                log.info("Artwork created with ID: {}", id);
                return findById(id);
            }
        } catch (SQLException e) {
            log.error("Error creating artwork: {}", entity, e);
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create artwork: " + entity);
    }

    private void setNullBigDecimal(int index, BigDecimal value, PreparedStatement statement) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.DOUBLE);
        } else {
            statement.setBigDecimal(index, value);
        }
    }

    @Override
    public Artwork findById(Long id) {
        log.info("Finding artwork by ID: {}", id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            log.error("Error finding artwork with ID: {}", id, e);
            throw new RuntimeException(e);
        }
        return null;
    }

    private Artwork mapRow(ResultSet resultSet) throws SQLException {
        Artwork artwork = new Artwork();
        artwork.setId(resultSet.getLong("id"));
        artwork.setTitle(resultSet.getString("title"));
        artwork.setExecutionType(ExecutionType.valueOf(resultSet.getString("execution")));
        artwork.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
        artwork.setHeight(resultSet.getBigDecimal("height"));
        artwork.setWidth(resultSet.getBigDecimal("width"));
        artwork.setVolume(resultSet.getBigDecimal("volume"));
        Long artistId = resultSet.getLong("artist_id");
        ArtistDao artistDao = new ArtistDaoImpl(this.dataSource);
        artwork.setArtistId(artistDao.findById(artistId));
        return artwork;
    }

    @Override
    public List<Artwork> findAll() {
        log.info("Finding all artworks");
        List<Artwork> artworks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Artwork artwork = mapRow(resultSet);
                artworks.add(artwork);
            }
        } catch (SQLException e) {
            log.error("Error finding all artworks", e);
            throw new RuntimeException(e);
        }
        return artworks;
    }

    @Override
    public Artwork update(Artwork entity) {
        log.info("Updating artwork: {}", entity);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getExecutionType().toString());
            statement.setDate(3, Date.valueOf(entity.getCreationDate()));
            setNullBigDecimal(4, entity.getHeight(), statement);
            setNullBigDecimal(5, entity.getWidth(), statement);
            setNullBigDecimal(6, entity.getVolume(), statement);
            statement.setLong(7, entity.getArtistId().getId());
            statement.setLong(8, entity.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Artwork updated successfully with ID: {}", entity.getId());
                return findById(entity.getId());
            } else {
                log.warn("Failed to update artwork. No rows affected for ID: {}", entity.getId());
                throw new RuntimeException("Failed to update artwork. No rows affected.");
            }
        } catch (SQLException e) {
            log.error("Error updating artwork: {}", entity, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting artwork with ID: {}", id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Artwork deleted successfully with ID: {}", id);
                return true;
            } else {
                log.warn("Failed to delete artwork with ID: {}", id);
                return false;
            }
        } catch (SQLException e) {
            log.error("Error deleting artwork with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }
}