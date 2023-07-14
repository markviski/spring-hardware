package edu.bbte.idde.vmim1980.backend.dao.jdbc;

import edu.bbte.idde.vmim1980.backend.dao.HardwareDao;
import edu.bbte.idde.vmim1980.backend.model.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class HardwareJdbcDao implements HardwareDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareJdbcDao.class);

    private PreparedStatement queryBuilder(PreparedStatement query, Hardware entity) {
        try {
            query.setFloat(1, entity.getPrice());
            query.setString(2, entity.getBrand());
            query.setString(3, entity.getModel());
            query.setInt(4, entity.getYear());
            query.setInt(5, entity.getMemory());
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return query;
    }

    private Hardware getEntityFromResultSet(ResultSet set) {
        try {
            Hardware hardwareEntity = new Hardware(set.getFloat(2), set.getString(3),
                    set.getString(4), set.getInt(5), set.getInt(6));
            hardwareEntity.setId(set.getLong(1));
            return hardwareEntity;
        } catch (SQLException e) {
            LOGGER.error("Could not get Hardware entity from result set.");
        }
        return null;
    }

    @Override
    public void create(Hardware entity) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("insert into "
                    + "Hardware(price, brand, model, year, mem) values (?, ?, ?, ?, ?)");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.executeUpdate();
            LOGGER.info("Hardware create successful.");
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }

    @Override
    public Hardware read(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Hardware WHERE id = ?");
            query.setLong(1, id);
            ResultSet set = query.executeQuery();
            LOGGER.info("Hardware read successful.");
            if (set.next()) {
                return getEntityFromResultSet(set);
            }
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return null;
    }

    @Override
    public void update(Hardware entity, Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("update Hardware set price=?, brand=?,"
                    + " model=?, year=?, mem=? where id=? ");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.setLong(6, id);
            query.executeUpdate();
            LOGGER.info("Hardware update successful.");
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("delete from Hardware where id = ?");
            query.setLong(1, id);
            query.executeUpdate();
            LOGGER.info("Hardware delete successful.");
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }

    @Override
    public Collection<Hardware> readAll() {
        Collection<Hardware> hardwareCollection = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Hardware");
            ResultSet set = query.executeQuery();
            LOGGER.info("Hardware readAll successful.");
            while (set.next()) {
                hardwareCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return hardwareCollection;
    }

    @Override
    public Collection<Hardware> findByBrand(String brand) {
        Collection<Hardware> hardwareCollection = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Hardware WHERE brand = ?");
            query.setString(1, brand);
            ResultSet set = query.executeQuery();
            LOGGER.info("Hardware findByBrand successful.");
            while (set.next()) {
                hardwareCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return hardwareCollection;
    }

}
