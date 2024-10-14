package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ArtworkDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Artwork;

import java.util.List;

public class ArtworkDaoImpl implements ArtworkDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "";
    private static final String FIND_BY_ID_QUERY = "";
    private static final String FIND_ALL_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public ArtworkDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Artwork create(Artwork entity) {
        return null;
    }

    @Override
    public Artwork findById(Long id) {
        return null;
    }

    @Override
    public List<Artwork> findAll() {
        return null;
    }

    @Override
    public Artwork update(Artwork entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
