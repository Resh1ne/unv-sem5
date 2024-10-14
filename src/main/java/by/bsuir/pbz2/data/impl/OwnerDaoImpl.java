package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.OwnerDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Owner;

import java.util.List;

public class OwnerDaoImpl implements OwnerDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "";
    private static final String FIND_BY_ID_QUERY = "";
    private static final String FIND_ALL_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public OwnerDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Owner create(Owner entity) {
        return null;
    }

    @Override
    public Owner findById(Long id) {
        return null;
    }

    @Override
    public List<Owner> findAll() {
        return null;
    }

    @Override
    public Owner update(Owner entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
