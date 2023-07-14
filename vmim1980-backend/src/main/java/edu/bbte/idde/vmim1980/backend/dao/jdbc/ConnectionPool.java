package edu.bbte.idde.vmim1980.backend.dao.jdbc;

import edu.bbte.idde.vmim1980.backend.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.vmim1980.backend.config.ConfigFactory;

import java.sql.*;

public class ConnectionPool {
    private static HikariDataSource datasrc;
    private static Config configsrc = ConfigFactory.getConfig();

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(configsrc.getDaoUrl());
        config.setUsername(configsrc.getDaoUser());
        config.setPassword(configsrc.getDaoPass());
        config.setMaximumPoolSize(configsrc.getDaoPoolsize());
        config.setDriverClassName(configsrc.getDaoDriver());

        datasrc = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return datasrc.getConnection();
    }
}

