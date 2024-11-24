package by.bsuir.pbz2.data.dao.impl;

import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.dao.ArtistDao;
import by.bsuir.pbz2.data.entity.Artist;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
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

    @Override
    public Artist create(Artist entity) {
        log.info("Creating artist: {}", entity.getName());
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
                log.info("Artist created with ID: {}", id);
                return findById(id);
            }

        } catch (SQLException e) {
            log.error("Error creating artist: {}", entity.getName(), e);
            throw new RuntimeException(e);
        }
        log.error("Can't create artist: {}", entity);
        throw new RuntimeException("Can't create artist: " + entity);
    }

    @Override
    public Artist findById(Long id) {
        log.info("Finding artist by ID: {}", id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Artist artist = mapRow(resultSet);
                log.info("Found artist: {}", artist.getName());
                return artist;
            }
        } catch (SQLException e) {
            log.error("Error finding artist by ID: {}", id, e);
            throw new RuntimeException(e);
        }
        log.warn("Artist with ID: {} not found", id);
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
        log.info("Finding all artists");
        List<Artist> artists = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Artist artist = mapRow(resultSet);
                artists.add(artist);
                log.info("Found artist: {}", artist.getName());
            }
        } catch (SQLException e) {
            log.error("Error finding all artists", e);
            throw new RuntimeException(e);
        }
        return artists;
    }

    @Override
    public Artist update(Artist entity) {
        log.info("Updating artist with ID: {}", entity.getId());
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
                log.info("Artist with ID: {} updated successfully", entity.getId());
                return findById(entity.getId());
            } else {
                log.error("Failed to update artist. No rows affected for ID: {}", entity.getId());
                throw new RuntimeException("Failed to update artist. No rows affected.");
            }
        } catch (SQLException e) {
            log.error("Error updating artist with ID: {}", entity.getId(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting artist with ID: {}", id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Artist with ID: {} deleted successfully", id);
                return true;
            } else {
                log.warn("Failed to delete artist with ID: {}. No rows affected.", id);
                return false;
            }
        } catch (SQLException e) {
            log.error("Error deleting artist with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }
}