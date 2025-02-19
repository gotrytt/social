package com.gotryt.coop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.coop.exception.UserException;
import com.gotryt.coop.model.User;
import com.gotryt.coop.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/{userId}/update")
    public User updateUser(@PathVariable Long userId, @RequestBody User user, @RequestHeader("Authorization") String jwt) throws UserException {
        return userService.updateUser(userId, user);
    } 

    @PutMapping("/user/{userId}/activate")
    public User activateUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        return userService.activateUser(userId);
    }

}
