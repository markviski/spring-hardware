package edu.bbte.idde.vmim1980.spring.dao.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!mem")
public class ConnectionPool {

    @Value("${jdbc.daoUrl}")
    private String daoUrl;

    @Value("${jdbc.daoUser}")
    private String daoUser;

    @Value("${jdbc.daoPass}")
    private String daoPass;

    @Value("${jdbc.daoPoolsize}")
    private Integer daoPoolsize;

    @Value("${jdbc.daoDriver}")
    private String daoDriver;

    @Bean
    @Primary
    public HikariDataSource getDataSource() {
        HikariDataSource datasrc = new HikariDataSource();
        datasrc.setJdbcUrl(daoUrl);
        datasrc.setUsername(daoUser);
        datasrc.setPassword(daoPass);
        datasrc.setMaximumPoolSize(daoPoolsize);
        datasrc.setDriverClassName(daoDriver);

        return datasrc;
    }
}
