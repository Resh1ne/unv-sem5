package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ExhibitionDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Exhibition;

import java.util.List;

public class ExhibitionDaoImpl implements ExhibitionDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "";
    private static final String FIND_BY_ID_QUERY = "";
    private static final String FIND_ALL_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public ExhibitionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Exhibition create(Exhibition entity) {
        return null;
    }

    @Override
    public Exhibition findById(Long id) {
        return null;
    }

    @Override
    public List<Exhibition> findAll() {
        return null;
    }

    @Override
    public Exhibition update(Exhibition entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
