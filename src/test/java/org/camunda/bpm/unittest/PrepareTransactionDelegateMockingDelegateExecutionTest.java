package org.camunda.bpm.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.workflow.dto.PerformTransactionRequest;
import com.poc.workflow.service.PrepareTransactionDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrepareTransactionDelegateMockingDelegateExecutionTest {

    @InjectMocks
    private PrepareTransactionDelegate delegate;

    @Mock
    private DelegateExecution execution;

    @Mock
    private RestTemplate restTemplate;

    String performTransactionRequestString = "{\n" +
            "    \"userToken\": \"eyJraWQiOiJ3T1h2SjJGckpPSUxSMCtwbGo3c1lYZGszK2QybEFYR0I4Nm9hWmNZdFo0PSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI2NWs5bGR0dXNqaWgwMDk2ZzI2bWdkdDVwZCIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoib3ZlcmxlZGdlclwvcmVhZC5zY29wZSIsImF1dGhfdGltZSI6MTcwOTU2MTI1MywiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfcWVyZnVVQzBiIiwiZXhwIjoxNzA5NTY0ODUzLCJpYXQiOjE3MDk1NjEyNTMsInZlcnNpb24iOjIsImp0aSI6IjRjYmFiNjFmLWIxODktNGM2Ni1hMGVhLTk0YjRlZGFlMDQwZSIsImNsaWVudF9pZCI6IjY1azlsZHR1c2ppaDAwOTZnMjZtZ2R0NXBkIn0.dWwzqj91V2PPDWuYWddD_5_V9DYdEWE7ir-Wl8NFVXLAHF58nIVG6TuQdMv2MC77alix4JYjopP1axvWJRa-9bVSxcHf0KZas7qPM2qt-h-eyGkXSkRk81iiRFxGLldtDXT67mUe2E0BzItYDJU4q19C5fLD4tTgvZiIIQXs_miCehyud2o1UMK2htItVL7Uh8_ZWq9BFgZSYG2adgVgU3jJ82szCLeAOlj4NVWuiifA3WSbL2I0yYhNp_tLGHz1cJfboDyThcurVNqObyjYLrVfYAIYgJ6HzI6zb4jMKWga6d6DgsjWdGnVndwHu3ozg5ekfm7NJlAyZjHGaSKUbA\",\n" +
            "    \"transactionSigningResponderName\": \"tsr-jks\",\n" +
            "    \"keyId\": \"0x009B27920005F9b6Cc0Ae594331A5F53399B6185\",\n" +
            "    \"preparedTransaction\": {\n" +
            "        \"type\": \"PAYMENT\",\n" +
            "        \"location\": {\n" +
            "            \"technology\": \"Ethereum\",\n" +
            "            \"network\": \"polygon mumbai testnet\"\n" +
            "        },\n" +
            "        \"urgency\": \"normal\",\n" +
            "        \"requestDetails\": {\n" +
            "            \"overledgerSigningType\": \"overledger-javascript-library\",\n" +
            "            \"message\": \"OVL Test Message\",\n" +
            "            \"origin\": [\n" +
            "                {\n" +
            "                    \"originId\": \"0x708d1C75e5880a9942f49e17483Cb8d58118D395\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"destination\": [\n" +
            "                {\n" +
            "                    \"destinationId\": \"0x708d1C75e5880a9942f49e17483Cb8d58118D395\",\n" +
            "                    \"payment\": {\n" +
            "                        \"amount\": \"1\",\n" +
            "                        \"unit\": \"MATIC\"\n" +
            "                    }\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }\n" +
            "}";

    String prepareTransactionResponse = "{\n" +
            "    \"requestId\": \"4d67ee79-1001-4a67-a674-d3a38f88517f\",\n" +
            "    \"gatewayFee\": {\n" +
            "        \"amount\": \"0\",\n" +
            "        \"unit\": \"QNT\"\n" +
            "    },\n" +
            "    \"dltFee\": {\n" +
            "        \"amount\": \"0.00002127200063816\",\n" +
            "        \"unit\": \"MATIC\"\n" +
            "    },\n" +
            "    \"nativeData\": {\n" +
            "        \"nonce\": 19,\n" +
            "        \"chainId\": 80001,\n" +
            "        \"chain\": \"mumbai testnet\",\n" +
            "        \"hardfork\": \"london\",\n" +
            "        \"to\": \"0x708d1C75e5880a9942f49e17483Cb8d58118D395\",\n" +
            "        \"gas\": \"21272\",\n" +
            "        \"maxFeePerGas\": \"1000000030\",\n" +
            "        \"maxPriorityFeePerGas\": \"1000000000\",\n" +
            "        \"value\": \"1000000000000000000\",\n" +
            "        \"data\": \"000000004f564c2054657374204d657373616765\"\n" +
            "    }\n" +
            "}";

    @Test //Test passing successfully
    public void testDelegateExecution() throws Exception {
        delegate = new PrepareTransactionDelegate(restTemplate);
        execution = mock(DelegateExecution.class);

        ObjectMapper objectMapper = new ObjectMapper();

        PerformTransactionRequest performTransactionRequest = objectMapper.readValue(performTransactionRequestString, PerformTransactionRequest.class);

        when(execution.getVariable("performTransactionRequest")).thenReturn(performTransactionRequest);

        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn(String.valueOf(ResponseEntity.ok(prepareTransactionResponse)));

        delegate.execute(execution);

        verify(execution).setVariable(any(), any());
    }
}
