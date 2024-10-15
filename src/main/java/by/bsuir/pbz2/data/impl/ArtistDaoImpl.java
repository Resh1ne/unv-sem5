package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ArtistDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Artist;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistDaoImpl implements ArtistDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "INSERT INTO artists " +
            "(name, birth_place, birth_date, biography, education) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name, birth_place, birth_date, biography, education " +
            "FROM artists WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT id, name, birth_place, birth_date, biography, education " +
            "FROM artists";
    private static final String UPDATE_QUERY = "UPDATE artists " +
            "SET " +
            "name = ?, " +
            "birth_place = ?, " +
            "birth_date = ?, " +
            "biography = ?, " +
            "education = ? " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM artists WHERE id = ?";

    public ArtistDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Artist create(Artist entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getBirthPlace());
            statement.setDate(3, Date.valueOf(entity.getBirthDate()));
            statement.setString(4, entity.getBiography());
            statement.setString(5, entity.getEducation());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                return findById(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create artist: " + entity);
    }

    @Override
    public Artist findById(Long id) {
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

    private static Artist mapRow(ResultSet resultSet) throws SQLException {
        Artist artist = new Artist();
        artist.setId(resultSet.getLong("id"));
        artist.setName(resultSet.getString("name"));
        artist.setBirthPlace(resultSet.getString("birth_place"));
        artist.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
        artist.setBiography(resultSet.getString("biography"));
        artist.setEducation(resultSet.getString("education"));
        return artist;
    }

    @Override
    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Artist artist = mapRow(resultSet);
                artists.add(artist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artists;
    }

    @Override
    public Artist update(Artist entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getBirthPlace());
            statement.setDate(3, Date.valueOf(entity.getBirthDate()));
            statement.setString(4, entity.getBiography());
            statement.setString(5, entity.getEducation());
            statement.setLong(6, entity.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return findById(entity.getId());
            } else {
                throw new RuntimeException("Failed to update artist. No rows affected.");
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
