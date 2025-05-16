package com.example.workflow.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


// Rest controller to handle HTTP request
@RestController
public class ProcessController {
    //start and manage process instances
    @Autowired
    private RuntimeService runtimeService;
    //to query process definitions
    @Autowired
    private org.camunda.bpm.engine.RepositoryService repositoryService;

    // endpoint
    @GetMapping("/start-process")
    public String startProcess() {
        try {
            // Get the latest version of the process definition
            ProcessDefinition process = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey("sample-process")
                    .latestVersion()
                    .singleResult();
            if (process == null) {
                return "Error: Process 'sample-process' not found";
            }
            runtimeService.startProcessInstanceByKey("sample-process");
            return "Process started! Definition: " + process.getId();
        } catch (Exception e) {
            return "Error starting process: " + e.getMessage();
        }
    }
}

