package com.gotryt.coop.service;

import com.gotryt.coop.dto.AuthResponse;
import com.gotryt.coop.dto.LoginDto;
import com.gotryt.coop.dto.UserRequest;
import com.gotryt.coop.exception.UserException;
import com.gotryt.coop.model.User;

public interface UserService {

    AuthResponse createAccount(UserRequest userRequest);
    AuthResponse login(LoginDto loginDto);
    AuthResponse findUserProfileByJwt(String jwt);    
    User findUserById(Long userId) throws UserException;    
    public User updateUser(Long userId, User user) throws UserException;
    public User activateUser(Long userId) throws UserException;

}
