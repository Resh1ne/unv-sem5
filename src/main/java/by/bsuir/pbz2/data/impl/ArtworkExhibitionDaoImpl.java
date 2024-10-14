package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ArtworkExhibitionDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;

public class ArtworkExhibitionDaoImpl implements ArtworkExhibitionDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "";
    private static final String FIND_BY_ARTWORK_ID_QUERY = "";
    private static final String FIND_BY_EXHIBITION_ID_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public ArtworkExhibitionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArtworkExhibition create(ArtworkExhibition artworkExhibition) {
        return null;
    }

    @Override
    public ArtworkExhibition findByArtworkId(Artwork artworkId) {
        return null;
    }

    @Override
    public ArtworkExhibition findByExhibitionId(Exhibition exhibitionId) {
        return null;
    }

    @Override
    public ArtworkExhibition update(ArtworkExhibition artworkExhibition) {
        return null;
    }

    @Override
    public boolean delete(ArtworkExhibition artworkExhibition) {
        return false;
    }
}
