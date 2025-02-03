package com.gotryt.twitter.service;

import java.util.List;

import com.gotryt.twitter.exception.PostException;
import com.gotryt.twitter.exception.UserException;
import com.gotryt.twitter.model.Post;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.request.ReplyRequest;

public interface PostService {

    public Post createPost(Post request, User user) throws UserException;
    public List<Post> findAllPost();
    public Post repost(Long postId, User user) throws UserException, PostException;
    public Post findById(Long postId) throws PostException;
    public void deletePostById(Long postId, Long userId) throws PostException, UserException;
    public Post removeFromRepost(Long postId, Long userId) throws PostException, UserException;
    public Post createReply(ReplyRequest request, User user) throws PostException, UserException;
    public List<Post> getUserPost(User user);
    public List<Post> findByLikesContainsUser(User user);

    
}
