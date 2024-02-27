package com.poc.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PrepareTransactionDelegate implements JavaDelegate {

    protected final RestTemplate restTemplate;

    private static String URL_PREPARE_TRANSACTION = "https://api.qa-sandbox.testing.quantnetwork.net/v2/preparation/transaction";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Starting Prepare Transaction Process... ");

        ObjectMapper objectMapper = new ObjectMapper();
        String performTransactionRequestString = objectMapper.writeValueAsString(execution.getVariable("performTransactionRequest"));
        PerformTransactionRequest performTransactionRequest = objectMapper.readValue(performTransactionRequestString, PerformTransactionRequest.class);
        String prepareTransactionString = objectMapper.writeValueAsString(performTransactionRequest.getPreparedTransaction());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + performTransactionRequest.getUserToken());

        HttpEntity<String> requestEntity = new HttpEntity<>(prepareTransactionString, headers);

        String prepareTransaction = restTemplate.postForObject(URL_PREPARE_TRANSACTION, requestEntity, String.class);

        //serialize a java object into JSON and stored it in this way so Camunda knows it is JSON
        ObjectValue prepareTransactionResponse = Variables
                .objectValue(prepareTransaction)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

        //add json object value as process variable
        execution.setVariable("prepareTransactionResponse", prepareTransactionResponse);

        log.info("Prepare Transaction Request Response: " + prepareTransactionResponse);
        log.info("Process finished at: {}", LocalDateTime.now());
    }
}
