package com.gotryt.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotryt.twitter.exception.PostException;
import com.gotryt.twitter.model.Like;
import com.gotryt.twitter.model.Post;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.repository.LikeRepository;
import com.gotryt.twitter.repository.PostRepository;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;

    @Override
    public Like likePost(Long postId, User user) throws PostException {
        Like isLikeExist = likeRepository.isLikeExist(user.getId(), postId);
        
        if (isLikeExist != null) {
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }
        Post post = postService.findById(postId);

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);

        post.getLikes().add(savedLike);
        postRepository.save(post);

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long postId) throws PostException {

        // Post post = postService.findById(postId);
        List<Like> likes = likeRepository.findByPostId(postId);
        return likes;
    }

}
