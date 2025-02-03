package com.gotryt.twitter.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String image;
    private String cover;
    private String location;
    private String website;
    private LocalDate dob;
    private String phone;
    private String bio;
    private String profession;
    private LocalDateTime createdAt;
    private boolean req_user;
    private boolean google_login;

    private List<UserDto> followers = new ArrayList<>();
    private List<UserDto> following = new ArrayList<>();

    private boolean followed;
    private boolean isVerified;
}
