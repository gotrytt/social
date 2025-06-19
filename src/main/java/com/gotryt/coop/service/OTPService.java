package com.gotryt.coop.service;

import com.gotryt.coop.dto.CoopResponse;
import com.gotryt.coop.dto.OTPRequest;

public interface OTPService {

    public CoopResponse sendOTP(OTPRequest otpRequest);
    public CoopResponse validateOTP(OTPRequest otpRequest);
    
}
