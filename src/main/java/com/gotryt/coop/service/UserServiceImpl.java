package com.gotryt.coop.service;

import com.gotryt.coop.dto.UserRequest;
import com.gotryt.coop.exception.UserException;
import com.gotryt.coop.model.Role;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.UserRepository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gotryt.coop.config.JwtProvider;
import com.gotryt.coop.dto.AuthResponse;
import com.gotryt.coop.dto.LoginDto;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider JwtProvider;

    @Override
    public AuthResponse createAccount(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())){
            return AuthResponse.builder()
                    .responseCode("419")
                    .responseMessage("email already exist!")
                    .jwt(null)
                .build();
        }

        if (userRepository.existsByMemship(userRequest.getMemship())){
            return AuthResponse.builder()
                    .responseCode("419")
                    .responseMessage("membership ID already exist!")
                    .jwt(null)
                .build();
        }

        User newUser = User.builder()
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .middleName(userRequest.getMiddleName())
            .gender(userRequest.getGender())
            .address(userRequest.getAddress())
            .state(userRequest.getState())
            .phone(userRequest.getPhone())
            .phone2(userRequest.getPhone2())
            .passport(userRequest.getPassport())
            .email(userRequest.getEmail())
            .dob(userRequest.getDob())
            .marital(userRequest.getMarital())
            .psn(userRequest.getPsn())
            .lga(userRequest.getLga())
            .station(userRequest.getStation())
            .password("123456")
            .memship(userRequest.getMemship())
            .savingsBalance(BigDecimal.ZERO)
            .status("NEW")
            .role(Role.ROLE_MEMBER)
            // .role(Role.valueOf("ROLE_ADMIN"))
            .build();

        User createdUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getMemship(), userRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        // String names = createdUser.getFirstName() +" "+ createdUser.getLastName() +" "+ createdUser.getMiddleName();
        
        // EmailDetails emailDetails = EmailDetails.builder()
        //         .recipient(createdUser.getEmail())
        //         .subject("New account creation")
        //         .message("Congratulations, your account has been created \nYour account details are as follows: \n\nAccount Name:" + names + "\nAccount Number: " + createdUser.getMemship() +"\n")
        //     .build();

        // emailService.sendEmail(emailDetails);

        return AuthResponse.builder()
                .responseCode("100")
                .responseMessage("New user created successfully")
                .jwt(jwt)
                // .user(createdUser)
            .build();
    }

    @Override
    public AuthResponse login(LoginDto loginDto) {
    
        String memship = loginDto.getMemship();
        User user = userRepository.findByMemship(memship);
    
        if (user == null) {
            return AuthResponse.builder()
                .responseCode("419")
                .responseMessage("No user found")
                .jwt(null)
                .build();
        }

        // Use matches() for proper password verification
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return AuthResponse.builder()
                .responseCode("419")
                .responseMessage("Invalid password")
                .jwt(null)
                .build();
        }
    
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getMemship(), loginDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        String jwt = JwtProvider.generateToken(authentication);
    
        return AuthResponse.builder()
                .responseCode("100")
                .responseMessage("Login success")
                .jwt(jwt)
                .build();
    }
    
    @Override
    public AuthResponse findUserProfileByJwt(String jwt)  {

        String memship = JwtProvider.getMemshipFromToken(jwt);
        User user = userRepository.findByMemship(memship);

        if (user == null) {
            return AuthResponse.builder()
                .responseCode("419")
                .responseMessage("No user found")
                .jwt(null)
                // .user(user)
            .build();
        }

        return AuthResponse.builder()
                .responseCode("100")
                .responseMessage("user found with jwt")
                .jwt(jwt)
                .user(user)
            .build();
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserException("use not found with id: " + userId)
        );
        return user;
    }

    @Override
    public User updateUser(Long userId, User userData) throws UserException {
        
        // Retrieve the existing user or throw an exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        if (userData.getFirstName() != null) {
            user.setFirstName(userData.getFirstName());
        }
        if (userData.getMiddleName() != null) {
            user.setMiddleName(userData.getMiddleName());
        }
        if (userData.getLastName() != null) {
            user.setLastName(userData.getLastName());
        }
        if (userData.getGender() != null) {
            user.setGender(userData.getGender());
        }
        if (userData.getEmail() != null) {
            user.setEmail(userData.getEmail());
        }
        if (userData.getPassport() != null) {
            user.setPassport(userData.getPassport());
        }
        if (userData.getAddress() != null) {
            user.setAddress(userData.getAddress());
        }
        if (userData.getState() != null) {
            user.setState(userData.getState());
        }
        if (userData.getPassword() != null) {
            // Encode the new password before setting it
            user.setPassword(passwordEncoder.encode(userData.getPassword()));
        }
        if (userData.getPsn() != null) {
            user.setPsn(userData.getPsn());
        }
        if (userData.getMemship() != null) {
            user.setMemship(userData.getMemship());
        }
        if (userData.getPhone() != null) {
            user.setPhone(userData.getPhone());
        }
        if (userData.getPhone2() != null) {
            user.setPhone2(userData.getPhone2());
        }
        if (userData.getDob() != null) {
            user.setDob(userData.getDob());
        }
        if (userData.getStation() != null) {
            user.setStation(userData.getStation());
        }
        if (userData.getStatus() != null) {
            user.setStatus(userData.getStatus());
        }
        if (userData.getMarital() != null) {
            user.setMarital(userData.getMarital());
        }
        if (userData.getLga() != null) {
            user.setLga(userData.getLga());
        }

        return userRepository.save(user);
    }

    @Override
    public User activateUser(Long userId) throws UserException {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException("User not found"));

        user.setStatus("ACTIVE");

        return userRepository.save(user);
    }
}
