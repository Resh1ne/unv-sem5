package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ExhibitionHallDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.ExhibitionHall;

import java.util.List;

public class ExhibitionHallDaoImpl implements ExhibitionHallDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "";
    private static final String FIND_BY_ID_QUERY = "";
    private static final String FIND_ALL_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public ExhibitionHallDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ExhibitionHall create(ExhibitionHall entity) {
        return null;
    }

    @Override
    public ExhibitionHall findById(Long id) {
        return null;
    }

    @Override
    public List<ExhibitionHall> findAll() {
        return null;
    }

    @Override
    public ExhibitionHall update(ExhibitionHall entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
