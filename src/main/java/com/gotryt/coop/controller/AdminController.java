package com.gotryt.coop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.coop.exception.UserException;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.UserRepository;
import com.gotryt.coop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> allUsers(@RequestHeader("Authorization") String jwt) {
        return userRepository.findAll();
    }

    @GetMapping("/user/id/{userId}")
    public Optional<User> findUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        return userRepository.findById(userId);
    }

    @GetMapping("/user/memship/{memship}")
    public User findUserByMemship(@PathVariable String memship, @RequestHeader("Authorization") String jwt) throws UserException {
        return userRepository.findByMemship(memship);
    }

    @GetMapping("/users/new")
    public List<User> findNewUsers(@RequestHeader("Authorization") String jwt) throws UserException {
        return userRepository.findByStatus("new");
    } 

    @PutMapping("/user/{userId}/activate")
    public User activateUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        return userService.activateUser(userId);
    }

    @PutMapping("/user/update")
    public User updateUser(@RequestBody User user, @RequestHeader("Authorization") String jwt) throws UserException {
        return userService.updateUser(user);
    } 

    @PostMapping("/users/multi")
    public List<User> addUsers(@RequestBody List<User> users) throws UserException {
        return userService.addMultiUsers(users);
    }

    
}
