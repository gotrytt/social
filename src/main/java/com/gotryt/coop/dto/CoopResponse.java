package com.gotryt.coop.dto;

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
public class CoopResponse {

    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;

}
