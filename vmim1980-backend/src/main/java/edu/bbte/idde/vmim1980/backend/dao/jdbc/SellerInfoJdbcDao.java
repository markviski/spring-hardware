package edu.bbte.idde.vmim1980.backend.dao.jdbc;

import edu.bbte.idde.vmim1980.backend.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.backend.model.SellerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SellerInfoJdbcDao implements SellerInfoDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerInfoJdbcDao.class);

    private PreparedStatement queryBuilder(PreparedStatement query, SellerInfo entity) {
        try {
            query.setLong(1, entity.getHardwareid());
            query.setString(2, entity.getSeller());
            query.setInt(3, entity.getQuantity());
            query.setString(4, entity.getPhonenumber());
            query.setFloat(5, entity.getShippingfee());
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return query;
    }

    private SellerInfo getEntityFromResultSet(ResultSet set) {
        try {
            SellerInfo sellerInfoEntity = new SellerInfo(set.getLong(2), set.getString(3),
                    set.getInt(4), set.getString(5), set.getFloat(6));
            sellerInfoEntity.setId(set.getLong(1));
            return sellerInfoEntity;
        } catch (SQLException e) {
            LOGGER.error("Could not get SellerInfo entity from result set.");
        }
        return null;
    }

    @Override
    public void create(SellerInfo entity) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("insert into "
                    + "SellerInfo(hardwareid, seller, quantity, phonenumber, shippingfee) "
                    + "values (?, ?, ?, ?, ?)");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.executeUpdate();
            LOGGER.info("SellerInfo create successful.");
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }

    @Override
    public SellerInfo read(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM SellerInfo WHERE id = ?");
            query.setLong(1, id);
            ResultSet set = query.executeQuery();
            LOGGER.info("SellerInfo read successful.");
            if (set.next()) {
                return getEntityFromResultSet(set);
            }
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return null;
    }

    @Override
    public void update(SellerInfo entity, Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("update SellerInfo "
                    + "set hardwareid=?, seller=?, quantity=?, phonenumber=?, shippingfee=? where id=? ");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.setLong(6, id);
            query.executeUpdate();
            LOGGER.info("SellerInfo update successful.");
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("delete from SellerInfo where id = ?");
            query.setLong(1,id);
            query.executeUpdate();
            LOGGER.info("SellerInfo delete successful.");
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }

    @Override
    public Collection<SellerInfo> readAll() {
        Collection<SellerInfo> sellerInfoCollection = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM SellerInfo");
            ResultSet set = query.executeQuery();
            LOGGER.info("SellerInfo readAll successful.");
            while (set.next()) {
                sellerInfoCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return sellerInfoCollection;
    }

    @Override
    public Collection<SellerInfo> findBySeller(String seller) {
        Collection<SellerInfo> sellerInfoCollection = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM SellerInfo WHERE seller = ?");
            query.setString(1, seller);
            ResultSet set = query.executeQuery();
            LOGGER.info("SellerInfo findBySeller successful.");
            if (set.next()) {
                sellerInfoCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            LOGGER.error("Error occurred: ", e);
        }
        return sellerInfoCollection;
    }
}
