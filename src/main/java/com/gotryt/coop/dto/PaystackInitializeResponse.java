package com.gotryt.coop.dto;

import lombok.Data;

@Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
public class PaystackInitializeResponse {
    private boolean status;
    private String message;
    private PaystackData data;

    @Data
    public static class PaystackData {
        private String authorization_url;
        private String access_code;
        private String reference;
        // Getters and setters
    }
}
