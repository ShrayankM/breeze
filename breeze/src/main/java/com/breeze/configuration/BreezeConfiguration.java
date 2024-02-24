package com.breeze.configuration;

import com.breeze.util.LoggerWrapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class BreezeConfiguration {

    private static final LoggerWrapper LOGGER = LoggerWrapper.getLogger(BreezeConfiguration.class);

    @Value("${db.mysql.host.url}")
    private String databaseUrl;

    @Value("${db.mysql.database.name}")
    private String databaseName;

    @Value("${db.mysql.driver.class.name}")
    private String driverClassName;

    @Value("${db.mysql.username}")
    private String username;

    @Value("${db.mysql.password}")
    private String password;

    private void shutdown() {
        LOGGER.info("Shutting down application");
        Runtime.getRuntime().halt(1);
    }

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        StringBuilder urlBuilder = new StringBuilder(databaseUrl).append("/").append(databaseName).append("?serverTimezone=UTC");
        String dbUrl = urlBuilder.toString();

        dataSource.setJdbcUrl(dbUrl);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        try {
            LOGGER.info("Testing db connection for {}", dbUrl);
            Connection result = dataSource.getConnection();
            if (result == null) {
                throw new SQLException("Db connection failed for {} ", dbUrl);
            } else {
                LOGGER.info("Db connection passed for {}", dbUrl);
            }

        } catch (SQLException e) {
            LOGGER.info("Error occurred in db connection validation {}", dbUrl);
            LOGGER.error(e);
            shutdown();
        }
        return dataSource;
    }

}
