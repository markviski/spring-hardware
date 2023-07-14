package edu.bbte.idde.vmim1980.backend.dao;

import edu.bbte.idde.vmim1980.backend.config.Config;
import edu.bbte.idde.vmim1980.backend.config.ConfigFactory;
import edu.bbte.idde.vmim1980.backend.dao.jdbc.JdbcDaoFactory;
import edu.bbte.idde.vmim1980.backend.dao.mem.MemDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DaoFactory {
    private static DaoFactory instance;
    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactory.class);

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Config config = ConfigFactory.getConfig();
            String daoType = config.getDaoType();
            if ("jdbc".equals(daoType)) {
                LOGGER.info("DaoFactory: jdbc");
                instance = new JdbcDaoFactory();
            } else if ("mem".equals(daoType)) {
                LOGGER.info("DaoFactory: mem");
                instance = new MemDaoFactory();
            } else {
                LOGGER.warn("daoType value {} not supported, regressing to jdbc", daoType);
                instance = new JdbcDaoFactory();
            }
        }
        return instance;
    }

    public abstract HardwareDao getHardwareDao();

    public abstract SellerInfoDao getSellerInfoDao();
}
