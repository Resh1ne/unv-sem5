package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ArtworkDao;
import by.bsuir.pbz2.data.ArtworkExhibitionDao;
import by.bsuir.pbz2.data.ExhibitionDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtworkExhibitionDaoImpl implements ArtworkExhibitionDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "";
    private static final String FIND_BY_ARTWORK_ID_QUERY = "";
    private static final String FIND_BY_EXHIBITION_ID_QUERY = "";
    private static final String FIND_ALL_QUERY = "";

    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public ArtworkExhibitionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArtworkExhibition create(ArtworkExhibition artworkExhibition) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY);
            statement.setLong(1, artworkExhibition.getExhibitionId().getId());
            statement.setLong(2, artworkExhibition.getArtworkId().getId());
            statement.executeUpdate();
            return findByExhibitionId(artworkExhibition.getExhibitionId());
        } catch (SQLException e) {
            throw new RuntimeException("Can't create ArtworkExhibition: " + artworkExhibition + "\n" + e);
        }
    }

    @Override
    public ArtworkExhibition findByArtworkId(Artwork artworkId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ARTWORK_ID_QUERY);
            statement.setLong(1, artworkId.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public ArtworkExhibition findByExhibitionId(Exhibition exhibitionId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EXHIBITION_ID_QUERY);
            statement.setLong(1, exhibitionId.getId());
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
    public List<ArtworkExhibition> findAll() {
        List<ArtworkExhibition> artworkExhibitions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                ArtworkExhibition artworkExhibition = mapRow(resultSet);
                artworkExhibitions.add(artworkExhibition);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artworkExhibitions;
    }

    @Override
    public ArtworkExhibition update(ArtworkExhibition artworkExhibition) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setLong(1, artworkExhibition.getExhibitionId().getId());
            statement.setLong(2, artworkExhibition.getArtworkId().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return findByExhibitionId(artworkExhibition.getExhibitionId());
            } else {
                throw new RuntimeException("Failed to update ArtworkExhibition. No rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(ArtworkExhibition artworkExhibition) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, artworkExhibition.getExhibitionId().getId());
            statement.setLong(2, artworkExhibition.getArtworkId().getId());
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
