package com.gotryt.coop.dto;

import com.gotryt.coop.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String responseCode;
    private String responseMessage;
    private String jwt;
    private User user;

}
