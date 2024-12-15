package com.breeze.configuration;

import com.breeze.service.BreezeConfigService;
import com.breeze.service.impl.BreezeConfigServiceImpl;
import com.breeze.util.LoggerWrapper;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class BreezeConfiguration implements WebMvcConfigurer {

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

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;


    private void shutdown() {
        LOGGER.info("Shutting down application");
        Runtime.getRuntime().halt(1);
    }

    @Bean(name = "dataSource")
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

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.breeze.model");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
        emf.setJpaProperties(properties);

        return emf;
    }

    @Bean(name = "entityManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource, @Qualifier("entityManagerFactory")EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public BreezeConfigService breezeConfigService(@Qualifier("entityManager") PlatformTransactionManager transactionManager) {
        return new BreezeConfigServiceImpl();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://shvgxjk-anonymous-8081.exp.direct", "http://localhost:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
