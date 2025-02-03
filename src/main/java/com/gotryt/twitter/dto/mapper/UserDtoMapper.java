package com.gotryt.twitter.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.gotryt.twitter.dto.UserDto;
import com.gotryt.twitter.model.User;

public class UserDtoMapper {
    
    public static UserDto toUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setUsername(user.getUsername());
        userDto.setImage(user.getImage());
        userDto.setCover(user.getCover());
        userDto.setWebsite(user.getWebsite());
        userDto.setBio(user.getBio());
        userDto.setDob(user.getDob());
        userDto.setLocation(user.getLocation());
        userDto.setProfession(user.getProfession());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setPhone(user.getPhone());
        userDto.setGoogle_login(user.isGoogle_login());
        userDto.setFollowers(toUserDtos(user.getFollowers()));
        userDto.setFollowing(toUserDtos(user.getFollowing()));

        return userDto;
    }

    public static List<UserDto> toUserDtos(List<User> followers) {

        List<UserDto> userDtos = new ArrayList<>();

        for (User user : followers) {

            UserDto userDto = new UserDto();

            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setFullName(user.getFullName());
            userDto.setUsername(user.getUsername());
            userDto.setImage(user.getImage());

            userDtos.add(userDto);
        }
    
        return userDtos;
    }
}
