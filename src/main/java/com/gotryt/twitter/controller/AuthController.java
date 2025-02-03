package com.gotryt.twitter.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.twitter.config.JwtProvider;
import com.gotryt.twitter.exception.UserException;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.model.Verification;
import com.gotryt.twitter.repository.UserRepository;
import com.gotryt.twitter.response.AuthResponse;
import com.gotryt.twitter.service.CustomUserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

        System.out.println("user: " + user);
        
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        String fullName = user.getFullName();
        LocalDate dob = user.getDob();

        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserException("email is already used");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setUsername(username);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setDob(dob);
        createdUser.setVerification(new Verification());
        createdUser.setGoogle_login(false);

        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
            String username = user.getEmail();
            String password = user.getPassword();

            Authentication authentication = authenticate(username, password);

            String token = jwtProvider.generateToken(authentication);

            AuthResponse res = new AuthResponse(token, true);

            return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
        }

    private Authentication authenticate(String username, String password) {
        UserDetails userdetails = customUserDetails.loadUserByUsername(username);

        if (userdetails == null) {
            throw new BadCredentialsException("invalid username");
        }
        if (!passwordEncoder.matches(password, userdetails.getPassword())) {
            throw new BadCredentialsException("invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
    }
}
