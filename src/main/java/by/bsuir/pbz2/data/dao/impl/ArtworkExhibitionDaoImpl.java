package by.bsuir.pbz2.data.dao.impl;

import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.dao.ArtworkDao;
import by.bsuir.pbz2.data.dao.ArtworkExhibitionDao;
import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ArtworkExhibitionDaoImpl implements ArtworkExhibitionDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "CALL add_artwork_to_exhibition(?, ?)";
    private static final String FIND_BY_EXHIBITION_ARTWORK_ID_QUERY = "SELECT exhibition_id, artwork_id " +
            "FROM artwork_exhibitions WHERE exhibition_id = ? AND artwork_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT exhibition_id, artwork_id FROM artwork_exhibitions";
    private static final String DELETE_QUERY = "DELETE FROM artwork_exhibitions WHERE exhibition_id = ? AND artwork_id = ?";

    @Override
    public void create(ArtworkExhibition artworkExhibition) {
        log.info("Attempting to create ArtworkExhibition: {}", artworkExhibition);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY);
            statement.setLong(1, artworkExhibition.getExhibitionId().getId());
            statement.setLong(2, artworkExhibition.getArtworkId().getId());
            statement.executeUpdate();
            log.info("Successfully created ArtworkExhibition: {}", artworkExhibition);
        } catch (SQLException e) {
            log.error("Error creating ArtworkExhibition: {}", artworkExhibition, e);
            throw new RuntimeException("Can't create ArtworkExhibition: " + artworkExhibition, e);
        }
    }

    @Override
    public ArtworkExhibition findByExhibitionArtworkId(Artwork artworkId, Exhibition exhibitionId) {
        log.info("Attempting to find ArtworkExhibition with ExhibitionId: {}, ArtworkId: {}", exhibitionId, artworkId);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EXHIBITION_ARTWORK_ID_QUERY);
            statement.setLong(1, exhibitionId.getId());
            statement.setLong(2, artworkId.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ArtworkExhibition artworkExhibition = mapRow(resultSet);
                log.info("Found ArtworkExhibition: {}", artworkExhibition);
                return artworkExhibition;
            }
        } catch (SQLException e) {
            log.error("Error finding ArtworkExhibition with ExhibitionId: {}, ArtworkId: {}", exhibitionId, artworkId, e);
            throw new RuntimeException("Error finding ArtworkExhibition", e);
        }
        log.warn("No ArtworkExhibition found with ExhibitionId: {}, ArtworkId: {}", exhibitionId, artworkId);
        return null;
    }

    private ArtworkExhibition mapRow(ResultSet resultSet) throws SQLException {
        ArtworkExhibition artworkExhibition = new ArtworkExhibition();
        Long exhibitionId = resultSet.getLong("exhibition_id");
        ExhibitionDao exhibitionDao = new ExhibitionDaoImpl(this.dataSource);
        artworkExhibition.setExhibitionId(exhibitionDao.findById(exhibitionId));
        Long artworkId = resultSet.getLong("artwork_id");
        ArtworkDao artworkDao = new ArtworkDaoImpl(this.dataSource);
        artworkExhibition.setArtworkId(artworkDao.findById(artworkId));
        return artworkExhibition;
    }

    @Override
    public List<ArtworkExhibition> findAll() {
        log.info("Attempting to find all ArtworkExhibitions");
        List<ArtworkExhibition> artworkExhibitions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                ArtworkExhibition artworkExhibition = mapRow(resultSet);
                artworkExhibitions.add(artworkExhibition);
            }
            log.info("Found {} ArtworkExhibitions", artworkExhibitions.size());
        } catch (SQLException e) {
            log.error("Error finding all ArtworkExhibitions", e);
            throw new RuntimeException("Error retrieving all ArtworkExhibitions", e);
        }
        return artworkExhibitions;
    }

    @Override
    public boolean delete(ArtworkExhibition artworkExhibition) {
        log.info("Attempting to delete ArtworkExhibition: {}", artworkExhibition);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, artworkExhibition.getExhibitionId().getId());
            statement.setLong(2, artworkExhibition.getArtworkId().getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Successfully deleted ArtworkExhibition: {}", artworkExhibition);
                return true;
            } else {
                log.warn("No ArtworkExhibition found to delete: {}", artworkExhibition);
                return false;
            }
        } catch (SQLException e) {
            log.error("Error deleting ArtworkExhibition: {}", artworkExhibition, e);
            throw new RuntimeException("Error deleting ArtworkExhibition", e);
        }
    }
}