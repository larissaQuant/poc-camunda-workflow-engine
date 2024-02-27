package com.poc.workflow.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformTransactionRequest {

    String userToken;

    String transactionSigningResponderName;

    String keyId;

    Object preparedTransaction;
}
