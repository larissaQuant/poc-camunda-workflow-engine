package com.poc.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.poc.workflow.dto.PerformTransactionRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ExecuteTransactionDelegate implements JavaDelegate {

    private final RestTemplate restTemplate;
    private static String URL_EXECUTE_TRANSACTION = "https://api.qa-sandbox.testing.quantnetwork.net/v2/execution/transaction";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Starting Execute Transaction Process... ");

        ObjectMapper objectMapper = new ObjectMapper();

        String performTransactionRequestString = objectMapper.writeValueAsString(execution.getVariable("performTransactionRequest"));
        PerformTransactionRequest performTransactionRequest = objectMapper.readValue(performTransactionRequestString, PerformTransactionRequest.class);

        String signTransactionResponse = execution.getVariable("signTransactionResponse").toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + performTransactionRequest.getUserToken());

        HttpEntity<String> requestEntity = new HttpEntity<>(signTransactionResponse, headers);

        String executeTransaction = restTemplate.postForObject(URL_EXECUTE_TRANSACTION, requestEntity, String.class);

        ObjectValue executeTransactionResponse = Variables
                .objectValue(executeTransaction)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

        execution.setVariable("executeTransactionResponse", executeTransactionResponse);

        log.info("Execute Transaction Request Response: " + executeTransactionResponse);
        log.info("Process finished at: {}", LocalDateTime.now());
    }
}
