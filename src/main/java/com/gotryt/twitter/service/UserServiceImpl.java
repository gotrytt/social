package com.gotryt.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotryt.twitter.config.JwtProvider;
import com.gotryt.twitter.exception.UserException;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserException("use not found with id: " + userId)
        );
        return user;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("user not found with email" + email);
        }
        
        return user;
    }

    @Override
    public User updateUser(Long userId, User request) throws UserException {
        User user = findUserById(userId);

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getImage() != null) {
            user.setImage(request.getImage());
        }
        if (request.getCover() != null) {
            user.setCover(request.getCover());
        }
        if (request.getDob() != null) {
            user.setDob(request.getDob());
        }
        if (request.getLocation() != null) {
            user.setLocation(request.getLocation());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        if (request.getProfession() != null) {
            user.setProfession(request.getProfession());
        }
        if (request.getWebsite() != null) {
            user.setWebsite(request.getWebsite());
        }

        return userRepository.save(user);
    }

    @Override
    public User followUser(Long userId, User user) throws UserException {

        User userToFollow = findUserById(userId);
        
        if (user.getFollowing().contains(userToFollow) && userToFollow.getFollowers().contains(user)) {
            user.getFollowing().remove(userToFollow);
            userToFollow.getFollowers().remove(user);
        } else {
            user.getFollowing().add(userToFollow);
            userToFollow.getFollowers().add(user);
        }
        userRepository.save(userToFollow);
        userRepository.save(user);

        return userToFollow;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

}
