package com.gotryt.coop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gotryt.coop.model.OTP;


public interface OTPRepository extends JpaRepository<OTP, Long> {

    OTP findByEmail(String email);

}
