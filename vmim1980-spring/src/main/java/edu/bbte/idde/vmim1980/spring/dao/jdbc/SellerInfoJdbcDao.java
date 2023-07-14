package edu.bbte.idde.vmim1980.spring.dao.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.vmim1980.spring.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.spring.model.SellerInfo;
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
public class SellerInfoJdbcDao implements SellerInfoDao {
    @Autowired
    private HikariDataSource dataSource;

    private PreparedStatement queryBuilder(PreparedStatement query, SellerInfo entity) {
        try {
            query.setLong(1, entity.getHardwareid());
            query.setString(2, entity.getSeller());
            query.setInt(3, entity.getQuantity());
            query.setString(4, entity.getPhonenumber());
            query.setFloat(5, entity.getShippingfee());
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return query;
    }

    private SellerInfo getEntityFromResultSet(ResultSet set) {
        try {
            SellerInfo sellerInfoEntity = new SellerInfo();
            sellerInfoEntity.setHardwareid(set.getLong(2));
            sellerInfoEntity.setSeller(set.getString(3));
            sellerInfoEntity.setQuantity(set.getInt(4));
            sellerInfoEntity.setPhonenumber(set.getString(5));
            sellerInfoEntity.setShippingfee(set.getFloat(6));
            sellerInfoEntity.setId(set.getLong(1));
            return sellerInfoEntity;
        } catch (SQLException e) {
            log.error("Could not get SellerInfo entity from result set.");
        }
        return null;
    }

    @Override
    public SellerInfo saveAndFlush(SellerInfo entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("insert into "
                    + "SellerInfo(hardwareid, seller, quantity, phonenumber, shippingfee) "
                    + "values (?, ?, ?, ?, ?)");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.executeUpdate();
            log.info("SellerInfo create successful.");
            PreparedStatement query2 = connection.prepareStatement("SELECT * from sellerinfo "
                    + "where id = (SELECT MAX(id) from sellerinfo);");
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
    public SellerInfo getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM SellerInfo WHERE id = ?");
            query.setLong(1, id);
            ResultSet set = query.executeQuery();
            log.info("SellerInfo read successful.");
            if (set.next()) {
                return getEntityFromResultSet(set);
            }
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return null;
    }

    @Override
    public SellerInfo save(SellerInfo entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement queryEmpty = connection.prepareStatement("update SellerInfo "
                    + "set hardwareid=?, seller=?, quantity=?, phonenumber=?, shippingfee=? where id=? ");
            PreparedStatement query = queryBuilder(queryEmpty, entity);
            query.setLong(6, entity.getId());
            query.executeUpdate();
            log.info("SellerInfo update successful.");
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("delete from SellerInfo where id = ?");
            query.setLong(1,id);
            query.executeUpdate();
            log.info("SellerInfo delete successful.");
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
    }

    @Override
    public Collection<SellerInfo> findAll() {
        Collection<SellerInfo> sellerInfoCollection = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM SellerInfo");
            ResultSet set = query.executeQuery();
            log.info("SellerInfo readAll successful.");
            while (set.next()) {
                sellerInfoCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return sellerInfoCollection;
    }

    @Override
    public Collection<SellerInfo> findBySeller(String seller) {
        Collection<SellerInfo> sellerInfoCollection = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM SellerInfo WHERE seller = ?");
            query.setString(1, seller);
            ResultSet set = query.executeQuery();
            log.info("SellerInfo findBySeller successful.");
            if (set.next()) {
                sellerInfoCollection.add(getEntityFromResultSet(set));
            }
        } catch (SQLException e) {
            log.error("Error occurred: ", e);
        }
        return sellerInfoCollection;
    }
}
