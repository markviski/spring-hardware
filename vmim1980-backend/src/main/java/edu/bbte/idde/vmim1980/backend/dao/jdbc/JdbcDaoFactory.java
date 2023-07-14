package edu.bbte.idde.vmim1980.backend.dao.jdbc;

import edu.bbte.idde.vmim1980.backend.dao.HardwareDao;
import edu.bbte.idde.vmim1980.backend.dao.SellerInfoDao;
import edu.bbte.idde.vmim1980.backend.dao.DaoFactory;

public class JdbcDaoFactory extends DaoFactory {
    private HardwareDao hardwareDao;
    private SellerInfoDao sellerInfoDao;

    @Override
    public synchronized HardwareDao getHardwareDao() {
        if (hardwareDao == null) {
            hardwareDao = new HardwareJdbcDao();
        }
        return hardwareDao;
    }

    @Override
    public synchronized SellerInfoDao getSellerInfoDao() {
        if (sellerInfoDao == null) {
            sellerInfoDao = new SellerInfoJdbcDao();
        }
        return sellerInfoDao;
    }
}
