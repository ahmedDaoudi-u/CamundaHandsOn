package com.example.workflow.controller;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


// Rest controller to handle HTTP request
@RestController
public class ProcessController {
    //start and manage process instances
    @Autowired
    private RuntimeService runtimeService;
    //to query process definitions
    @Autowired
    private org.camunda.bpm.engine.RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService; // Queries process history

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

    // Lists all deployed process definitions
    // (working)
    @GetMapping("/process-definitions")
    public List<String> getProcessDefinitions() {
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                .list();
        return definitions.stream()
                .map(def -> "ID: " + def.getId() + ", Key: " + def.getKey() + ", Version: " + def.getVersion())
                .collect(Collectors.toList());
    }

    // Lists all running process instances (working)
    // URL: http://localhost:9090/process-instances
    @GetMapping("/process-instances")
    public List<String> getProcessInstances() {
        List<org.camunda.bpm.engine.runtime.ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .list();
        return instances.stream()
                .map(inst -> "ID: " + inst.getId() + ", Definition: " + inst.getProcessDefinitionId() + ", Status: " + (inst.isEnded() ? "Ended" : "Running"))
                .collect(Collectors.toList());
    }

    // Lists historical process instances (working)
    // URL: http://localhost:9090/process-history
    @GetMapping("/process-history")
    public List<String> getProcessHistory() {
        List<HistoricProcessInstance> history = historyService.createHistoricProcessInstanceQuery()
                .list();
        return history.stream()
                .map(h -> "ID: " + h.getId() + ", Definition: " + h.getProcessDefinitionKey() + ", Start: " + h.getStartTime() + ", End: " + h.getEndTime())
                .collect(Collectors.toList());
    }
}

