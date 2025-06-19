package com.gotryt.coop.dto;

import lombok.Data;

@Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
public class PaystackInitializeRequest {
    private String email;
    private int amount;
    private String callback_url; // ADD THIS FIELD
}

