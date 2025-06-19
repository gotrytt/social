package com.gotryt.coop.dto;

import com.gotryt.coop.model.Repay;

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
public class RepayResponse {

    private String responseCode;
    private String responseMessage;
    private Repay repay;

}
