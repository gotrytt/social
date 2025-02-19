package com.gotryt.coop.dto;

import java.time.LocalDate;

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
public class UserRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String email;
    private String passport;
    private String address;
    private String state;
    private String password;
    private String psn;
    private String memship;
    private String phone;
    private String phone2;
    private LocalDate dob;
    private String station;
    private String status;
    private String marital;
    private String lga;

}
