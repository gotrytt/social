package com.gotryt.coop.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.coop.dto.CoopResponse;
import com.gotryt.coop.dto.OTPRequest;
import com.gotryt.coop.service.OTPService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/otp")
@AllArgsConstructor
public class OTPController {

    private final OTPService otpService;

    @PostMapping("sendOTP")
    public CoopResponse sendOTP(@RequestBody OTPRequest otpRequest){
        return otpService.sendOTP(otpRequest);
    }

    @PostMapping("validateOTP")
    public CoopResponse validateOTP(@RequestBody OTPRequest otpRequest) {
        return otpService.validateOTP(otpRequest);
    }
    
}
