package com.gotryt.coop.service;

import com.gotryt.coop.dto.PaystackInitializeRequest;
import com.gotryt.coop.dto.PaystackInitializeResponse;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaystackService {

    @Value("${paystack.secret.key}")
    private String paystackSecretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public PaystackInitializeResponse initializeTransaction(PaystackInitializeRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(paystackSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaystackInitializeRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaystackInitializeResponse> response = restTemplate.postForEntity(
            "https://api.paystack.co/transaction/initialize",
            entity,
            PaystackInitializeResponse.class
        );

        return response.getBody();
    }

    public Map<String, Object> verifyTransaction(String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(paystackSecretKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            "https://api.paystack.co/transaction/verify/" + reference,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

}
