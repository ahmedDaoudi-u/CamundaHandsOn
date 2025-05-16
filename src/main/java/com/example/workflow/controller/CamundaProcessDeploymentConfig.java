package com.example.workflow.controller;

import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// will manually deploy Camunda BPMN processes on application startup
@Configuration
public class CamundaProcessDeploymentConfig {
    // to manage process deployments
    @Autowired
    private RepositoryService repositoryService;
    // Defines a CommandLineRunner to execute deployment logic after Spring Boot starts
    @Bean
    public CommandLineRunner deployProcesses() {
        return args -> {
            // Deploy sample-process.bpmn from the classpath (src/main/resources)
            repositoryService.createDeployment()
                    .addClasspathResource("sample-process.bpmn")
                    .deploy();
            System.out.println("Deployed sample-process manually");
        };
    }
}
