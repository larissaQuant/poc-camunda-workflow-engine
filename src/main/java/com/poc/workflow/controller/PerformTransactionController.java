package com.poc.workflow.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class PerformTransactionController {

    protected RuntimeService runtimeService;
    protected HistoryService historyService;

    @PostMapping(value = "/perform/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity performTransaction(@RequestBody Object performTransactionRequest) {
        log.info("Trigger received. Starting Perform Transaction Process...");

        Map<String, Object> instanceVariables = new HashMap<>(1);
        instanceVariables.put("performTransactionRequest", performTransactionRequest);

        ProcessInstance instance = runtimeService.startProcessInstanceByKey("performTransaction", instanceVariables);

        log.info("All service tasks executed. Process finished at: {}", LocalDateTime.now());

        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(instance.getId())
                .list();

        for (HistoricVariableInstance variable : variables) {
            if ("executeTransactionResponse".equals(variable.getName())) {
                String performTransactionResponse = variable.getValue().toString();
                return ResponseEntity.ok().body(performTransactionResponse);
            }
        }

        return ResponseEntity.ok().build();
    }
}
