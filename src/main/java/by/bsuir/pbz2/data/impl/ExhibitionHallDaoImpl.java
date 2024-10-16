package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.ExhibitionHallDao;
import by.bsuir.pbz2.data.OwnerDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.ExhibitionHall;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionHallDaoImpl implements ExhibitionHallDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "INSERT INTO exhibition_halls " +
            "(name, area, address, phone, owner_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name, area, address, phone, owner_id " +
            "FROM exhibition_halls " +
            "WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT id, name, area, address, phone, owner_id " +
            "FROM exhibition_halls ";
    private static final String UPDATE_QUERY = "UPDATE exhibition_halls " +
            "SET " +
            "name = ?, " +
            "area = ?, " +
            "address = ?, " +
            "phone = ?, " +
            "owner_id = ? " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM exhibition_halls WHERE id = ?";

    public ExhibitionHallDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ExhibitionHall create(ExhibitionHall entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            setNullBigDecimal(2, entity.getArea(), statement);
            statement.setString(3, entity.getAddress());
            statement.setString(4, entity.getPhone());
            statement.setLong(5, entity.getOwnerId().getId());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                return findById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create exhibition hall: " + entity);
    }

    private void setNullBigDecimal(int index, BigDecimal value, PreparedStatement statement) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.DOUBLE);
        } else {
            statement.setBigDecimal(index, value);
        }
    }

    @Override
    public ExhibitionHall findById(Long id) {
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

    private ExhibitionHall mapRow(ResultSet resultSet) throws SQLException {
        ExhibitionHall exhibitionHall = new ExhibitionHall();
        exhibitionHall.setId(resultSet.getLong("id"));
        exhibitionHall.setName(resultSet.getString("name"));
        exhibitionHall.setArea(resultSet.getBigDecimal("area"));
        exhibitionHall.setAddress(resultSet.getString("address"));
        exhibitionHall.setPhone(resultSet.getString("phone"));
        Long ownerId = resultSet.getLong("owner_id");
        OwnerDao ownerDao = new OwnerDaoImpl(this.dataSource);
        exhibitionHall.setOwnerId(ownerDao.findById(ownerId));
        return exhibitionHall;
    }

    @Override
    public List<ExhibitionHall> findAll() {
        List<ExhibitionHall> exhibitionHalls = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                ExhibitionHall exhibitionHall = mapRow(resultSet);
                exhibitionHalls.add(exhibitionHall);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exhibitionHalls;
    }

    @Override
    public ExhibitionHall update(ExhibitionHall entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, entity.getName());
            setNullBigDecimal(2, entity.getArea(), statement);
            statement.setString(3, entity.getAddress());
            statement.setString(4, entity.getPhone());
            statement.setLong(5, entity.getOwnerId().getId());
            statement.setLong(6, entity.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return findById(entity.getId());
            } else {
                throw new RuntimeException("Failed to update exhibition hall. No rows affected.");
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
