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
public class SignTransactionDelegate implements JavaDelegate {

    private final RestTemplate restTemplate;
    private static String URL_SIGN_TRANSACTION = "https://api.qa-sandbox.testing.quantnetwork.net/api/transaction-signing-sandbox";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Starting Sign Transaction Process... ");

        ObjectMapper objectMapper = new ObjectMapper();
        String performTransactionRequestString = objectMapper.writeValueAsString(execution.getVariable("performTransactionRequest"));
        PerformTransactionRequest performTransactionRequest = objectMapper.readValue(performTransactionRequestString, PerformTransactionRequest.class);

        String prepareTransactionResponse = execution.getVariable("prepareTransactionResponse").toString();
        ObjectNode signTransactionRequestObject = objectMapper.readValue(prepareTransactionResponse, ObjectNode.class);

        //adding fields to sign a transaction
        signTransactionRequestObject.put("transactionSigningResponderName", performTransactionRequest.getTransactionSigningResponderName());
        signTransactionRequestObject.put("keyId", performTransactionRequest.getKeyId());

        String signTransactionRequest = objectMapper.writeValueAsString(signTransactionRequestObject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + performTransactionRequest.getUserToken());
        headers.set("API-Version", "3.0.0");

        HttpEntity<String> requestEntity = new HttpEntity<>(signTransactionRequest, headers);

        String signTransaction = restTemplate.postForObject(URL_SIGN_TRANSACTION, requestEntity, String.class);

        ObjectValue signTransactionResponse = Variables
                .objectValue(signTransaction)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

        execution.setVariable("signTransactionResponse", signTransactionResponse);

        log.info("Sign Transaction Request Response: " + signTransactionResponse);
        log.info("Process finished at: {}", LocalDateTime.now());
    }

}
