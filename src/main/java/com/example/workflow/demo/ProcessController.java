package com.example.workflow.demo;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private org.camunda.bpm.engine.RepositoryService repositoryService;

    @GetMapping("/start-process")
    public String startProcess() {
        try {
            ProcessDefinition process = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey("sample-process")
                    .latestVersion()
                    .singleResult();
            if (process == null) {
                return "Error: Process 'sample-process' not found";
            }
            runtimeService.startProcessInstanceByKey("sample-process");
            return "Process started!";
        } catch (Exception e) {
            return "Error starting process: " + e.getMessage();
        }
    }
}
