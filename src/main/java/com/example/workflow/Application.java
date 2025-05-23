package com.example.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main entry point for the Spring Boot application, integrating Camunda BPM
@SpringBootApplication
public class Application {
  // Launches the Spring Boot application, initializing Camunda's process engine
  // and scanning for components

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

}