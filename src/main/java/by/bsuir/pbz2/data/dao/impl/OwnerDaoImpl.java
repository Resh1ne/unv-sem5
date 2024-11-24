package by.bsuir.pbz2.data.dao.impl;

import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.dao.OwnerDao;
import by.bsuir.pbz2.data.entity.Owner;
import by.bsuir.pbz2.data.entity.enums.OwnerType;
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
public class OwnerDaoImpl implements OwnerDao {
    private final DataSource dataSource;
    private static final String CREATION_QUERY = "INSERT INTO owners " +
            "(name, address, phone, type_id) " +
            "VALUES (?, ?, ?, (SELECT id FROM owner_types ot WHERE ot.owner_type = ?))";
    private static final String FIND_BY_ID_QUERY = "SELECT o.id, o.name, o.address, o.phone, ot.owner_type " +
            "FROM owners o " +
            "JOIN owner_types ot ON o.type_id = ot.id " +
            "WHERE o.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT o.id, o.name, o.address, o.phone, ot.owner_type " +
            "FROM owners o " +
            "JOIN owner_types ot ON o.type_id = ot.id ";
    private static final String UPDATE_QUERY = "UPDATE owners " +
            "SET " +
            "name = ?, " +
            "address = ?, " +
            "phone = ?, " +
            "type_id = (SELECT id FROM owner_types ot WHERE ot.owner_type = ?) " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM owners WHERE id = ?";

    @Override
    public Owner create(Owner entity) {
        log.info("Creating owner: {}", entity);
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
                log.info("Owner created with ID: {}", id);
                return findById(id);
            }
        } catch (SQLException e) {
            log.error("Error creating owner: {}", entity, e);
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't create owner: " + entity);
    }

    @Override
    public Owner findById(Long id) {
        log.info("Finding owner by ID: {}", id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Owner owner = mapRow(resultSet);
                log.info("Owner found: {}", owner);
                return owner;
            }
        } catch (SQLException e) {
            log.error("Error finding owner by ID: {}", id, e);
            throw new RuntimeException(e);
        }
        log.warn("Owner not found with ID: {}", id);
        return null;
    }

    private static Owner mapRow(ResultSet resultSet) throws SQLException {
        Owner owner = new Owner();
        owner.setId(resultSet.getLong("id"));
        owner.setName(resultSet.getString("name"));
        owner.setAddress(resultSet.getString("address"));
        owner.setPhone(resultSet.getString("phone"));
        owner.setOwnerType(OwnerType.valueOf(resultSet.getString("owner_type")));
        return owner;
    }

    @Override
    public List<Owner> findAll() {
        log.info("Finding all owners");
        List<Owner> owners = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                Owner owner = mapRow(resultSet);
                owners.add(owner);
            }
            log.info("Found {} owners", owners.size());
        } catch (SQLException e) {
            log.error("Error finding all owners", e);
            throw new RuntimeException(e);
        }
        return owners;
    }

    @Override
    public Owner update(Owner entity) {
        log.info("Updating owner: {}", entity);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getAddress());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getOwnerType().toString());
            statement.setLong(5, entity.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Owner updated with ID: {}", entity.getId());
                return findById(entity.getId());
            } else {
                log.warn("Failed to update owner, no rows affected: {}", entity);
                throw new RuntimeException("Failed to update owner. No rows affected.");
            }
        } catch (SQLException e) {
            log.error("Error updating owner: {}", entity, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting owner with ID: {}", id);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Owner deleted with ID: {}", id);
                return true;
            } else {
                log.warn("No owner found to delete with ID: {}", id);
                return false;
            }
        } catch (SQLException e) {
            log.error("Error deleting owner with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }
}