package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ArtistDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Artist;

import java.util.List;

public class ArtistDaoImpl implements ArtistDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "";
    private static final String FIND_BY_ID_QUERY = "";
    private static final String FIND_ALL_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public ArtistDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Artist create(Artist entity) {
        return null;
    }

    @Override
    public Artist findById(Long id) {
        return null;
    }

    @Override
    public List<Artist> findAll() {
        return null;
    }

    @Override
    public Artist update(Artist entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
