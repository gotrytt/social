package com.gotryt.coop.controller;

import com.gotryt.coop.dto.PaystackInitializeRequest;
import com.gotryt.coop.dto.PaystackInitializeResponse;
import com.gotryt.coop.service.PaystackService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaystackController {

    @Autowired
    private PaystackService paystackService;

    @PostMapping("/initialize")
    public ResponseEntity<PaystackInitializeResponse> initializePayment(@RequestBody PaystackInitializeRequest request) {
        PaystackInitializeResponse response = paystackService.initializeTransaction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify/{reference}")
    public ResponseEntity<?> verifyTransaction(@PathVariable String reference) {
        Map<String, Object> verificationResult = paystackService.verifyTransaction(reference);
        return ResponseEntity.ok(verificationResult);
    }

}
