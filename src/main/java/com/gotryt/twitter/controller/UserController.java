package com.gotryt.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.twitter.dto.UserDto;
import com.gotryt.twitter.dto.mapper.UserDtoMapper;
import com.gotryt.twitter.exception.UserException;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.service.UserService;
import com.gotryt.twitter.utils.UserUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        
        User user = userService.findUserProfileByJwt(jwt);
        UserDto userDto = UserDtoMapper.toUserDto(user);

        userDto.setReq_user(true);        

        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserByid(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user = userService.findUserById(userId);

        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(UserUtil.isReq_user(reqUser, user));
        // userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String query, @RequestHeader("Authorization") String jwt) throws UserException {
        
        // User reqUser = userService.findUserProfileByJwt(jwt);
        List<User> users = userService.searchUser(query);

        List<UserDto> userDtos = UserDtoMapper.toUserDtos(users);

        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody User user, @RequestHeader("Authorization") String jwt) throws UserException {
        
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user2 = userService.updateUser(reqUser.getId(), user);

        UserDto userDto = UserDtoMapper.toUserDto(user2);
        userDto.setReq_user(true);        

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}/follow")
    public ResponseEntity<UserDto> follow(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user2 = userService.followUser(userId, reqUser);

        UserDto userDto = UserDtoMapper.toUserDto(user2);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user2));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

}
