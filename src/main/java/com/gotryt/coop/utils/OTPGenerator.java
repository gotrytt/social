package com.gotryt.coop.utils;

import java.util.Random;

public class OTPGenerator {
    public static String generateOTP() {

        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        int count = 0;
        while (count < 5) {
            otp.append(random.nextInt(10));
            ++count;
        }

        return otp.toString();
    }

}
