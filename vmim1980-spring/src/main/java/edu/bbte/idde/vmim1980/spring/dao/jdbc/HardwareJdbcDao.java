package edu.bbte.idde.vmim1980.spring.dao.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.vmim1980.spring.dao.HardwareDao;
import edu.bbte.idde.vmim1980.spring.model.Hardware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Repository
@Profile("jdbc")
public class HardwareJdbcDao implements HardwareDao {
    @Autowired
    private HikariDataSource dataSource;

    private PreparedStatement queryBuilder(PreparedStatement query, Hardware entity) {
        try {
            query.setFloat(1, entity.getPrice());
            query.setString(2, entity.getBrand());
            query.setString(3, entity.getModel());
            query.setInt(4, entity.getYear());
            query.setInt(5, entity.getMemory());
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return query;
    }

    private Hardware getEntityFromResultSet(ResultSet set) {
        try {
            Hardware hardwareEntity = new Hardware();
            hardwareEntity.setPrice(set.getFloat(2));
            hardwareEntity.setBrand(set.getString(3));
            hardwareEntity.setModel(set.getString(4));
            hardwareEntity.setYear(set.getInt(5));
            hardwareEntity.setMemory(set.getInt(6));
            hardwareEntity.setId(set.getLong(1));
            return hardwareEntity;
        } catch (SQLException e) {
            log.error("Could not get Hardware entity from result set.");
        }
        return null;
    }

    @Override
    public Hardware saveAndFlush(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("insert into "
                    + "Hardware(price, brand, model, year, mem) values (?, ?, ?, ?, ?)");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.executeUpdate();
            log.info("Hardware create successful.");
            PreparedStatement query2 = connection.prepareStatement("SELECT * from Hardware "
                    + "where id = (SELECT MAX(id) from Hardware);");
            ResultSet set = query2.executeQuery();
            if (set.next()) {
                return getEntityFromResultSet(set);
            }
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return null;
    }

    @Override
    public Hardware getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Hardware WHERE id = ?");
            query.setLong(1, id);
            ResultSet set = query.executeQuery();
            log.info("Hardware read successful.");
            if (set.next()) {
                return getEntityFromResultSet(set);
            }
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return null;
    }

    @Override
    public Hardware save(Hardware entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("update Hardware set price=?, brand=?,"
                    + " model=?, year=?, mem=? where id=? ");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.setLong(6, entity.getId());
            query.executeUpdate();
            log.info("Hardware update successful.");
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("delete from Hardware where id = ?");
            query.setLong(1, id);
            query.executeUpdate();
            log.info("Hardware delete successful.");
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
    }

    @Override
    public Collection<Hardware> findAll() {
        Collection<Hardware> hardwareCollection = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Hardware");
            ResultSet set = query.executeQuery();
            log.info("Hardware readAll successful.");
            while (set.next()) {
                hardwareCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return hardwareCollection;
    }

    @Override
    public Collection<Hardware> findByBrand(String brand) {
        Collection<Hardware> hardwareCollection = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM Hardware WHERE brand = ?");
            query.setString(1, brand);
            ResultSet set = query.executeQuery();
            log.info("Hardware findByBrand successful.");
            while (set.next()) {
                hardwareCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return hardwareCollection;
    }

}
