package com.example.workflow.demo;

import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaProcessDeploymentConfig {

    @Autowired
    private RepositoryService repositoryService;

    @Bean
    public CommandLineRunner deployProcesses() {
        return args -> {
            repositoryService.createDeployment()
                    .addClasspathResource("my-project-process.bpmn")
                    .deploy();
            System.out.println("Deployed sample-process manually");
        };
    }
}
