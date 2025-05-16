package com.example.workflow.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
// Configures Camunda's process engine to integrate with Spring Boot and H2 database
@Configuration
public class CamundaConfig {

    // Spring's DataSource for the H2 database (jdbc:h2:file:./camunda-h2-database)
    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        // Set the H2 datasource
        config.setDataSource(dataSource);
        // Use Spring's transaction manager for database operations
        config.setTransactionManager(transactionManager);
        // Auto-update the database schema (creates ACT_* tables)
        config.setDatabaseSchemaUpdate("true");
        // Enable job executor for asynchronous tasks )
        config.setJobExecutorActivate(true);
        // Store full process history in ACT_HI_* tables
        config.setHistory("full");
        return config;
    }

    // Provides a transaction manager for Camunda and Spring JPA
    // what is spring JPA ? --> It is part of a larger data family it main use to make is
    // easier to manage data for date oriented applications
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
