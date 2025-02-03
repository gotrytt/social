package com.gotryt.twitter.service;

import java.util.List;

import com.gotryt.twitter.exception.PostException;
import com.gotryt.twitter.exception.UserException;
import com.gotryt.twitter.model.Like;
import com.gotryt.twitter.model.User;

public interface LikeService {

    public Like likePost(Long postId, User user) throws PostException, UserException;

    public List<Like> getAllLikes(Long postId) throws PostException;
    

}
