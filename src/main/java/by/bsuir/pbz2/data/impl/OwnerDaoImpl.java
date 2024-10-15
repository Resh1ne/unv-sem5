package by.bsuir.pbz2.data.impl;

import by.bsuir.pbz2.data.OwnerDao;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.entity.Owner;
import by.bsuir.pbz2.data.entity.enums.OwnerType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAddress());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getOwnerType().toString());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong("id");
                return findById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create owner: " + entity);
    }

    @Override
    public Owner findById(Long id) {
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

    private static Owner mapRow(ResultSet resultSet) throws SQLException {
        Owner owner = new Owner();
        owner.setId(resultSet.getLong("id"));
        owner.setName(resultSet.getString("name"));
        owner.setAddress(resultSet.getString("address"));
        owner.setPhone(resultSet.getString("phone"));
        owner.setOwnerType(OwnerType.valueOf(resultSet.getString("type_id")));
        return owner;
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Owner owner = mapRow(resultSet);
                owners.add(owner);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owners;
    }

    @Override
    public Owner update(Owner entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAddress());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getOwnerType().toString());
            statement.setLong(5, entity.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return findById(entity.getId());
            } else {
                throw new RuntimeException("Failed to update owner. No rows affected.");
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
