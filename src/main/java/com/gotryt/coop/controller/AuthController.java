package com.gotryt.coop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.coop.dto.AuthResponse;
import com.gotryt.coop.dto.LoginDto;
import com.gotryt.coop.dto.UserRequest;
import com.gotryt.coop.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public AuthResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping("/profile")
    public AuthResponse getUserProfile(@RequestHeader("Authorization") String jwt) {                          
        return userService.findUserProfileByJwt(jwt);
    }
    
}
