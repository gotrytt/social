package com.gotryt.coop.dto;

import java.math.BigDecimal;

// import com.gotryt.coop.model.LoanType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDto {

    private BigDecimal amount;
    private BigDecimal balance;
    private String type;
    private String purpose;
    private String remark;
    private String duration;
    private String bankName;
    private String aza;
    private String accountName;

}
