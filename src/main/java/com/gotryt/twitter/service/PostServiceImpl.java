package com.gotryt.twitter.service;

// import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotryt.twitter.exception.PostException;
import com.gotryt.twitter.exception.UserException;
import com.gotryt.twitter.model.Post;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.repository.PostRepository;
import com.gotryt.twitter.request.ReplyRequest;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post request, User user) throws UserException {

        Post post = new Post();
        post.setContent(request.getContent());
        post.setImage(request.getImage());
        post.setVideo(request.getVideo());
        post.setUser(user);
        post.setPost(true);
        post.setReply(false);
        // post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAllByIsPostTrueOrderByCreatedAtDesc();
    }

    @Override
    public Post repost(Long postId, User user) throws UserException, PostException {
        Post post = findById(postId);
        if (post.getRepostUsers().contains(user)) {
            post.getRepostUsers().remove(user);
        } else {
            post.getRepostUsers().add(user);
        }
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long postId) throws PostException {
        Post post = postRepository.findById(postId).orElseThrow(
        () -> new PostException("post not found with id: " + postId));

        return post;
    }

    @Override
    public void deletePostById(Long postId, Long userId) throws PostException, UserException {
        Post post = findById(postId);

        if (!userId.equals(post.getUser().getId())) {
            throw new UserException("you are not the post owner");
        }

        postRepository.deleteById(post.getId());
    }

    @Override
    public Post removeFromRepost(Long postId, Long userId) throws PostException, UserException {
        return null;
    }

    @Override
    public Post createReply(ReplyRequest request, User user) throws PostException, UserException {

        Post parentPost = findById(request.getPostId());

        Post reply = new Post();
        reply.setContent(request.getContent());
        reply.setImage(request.getImage());
        reply.setVideo(request.getVideo());
        reply.setUser(user);
        reply.setPost(false);
        reply.setReply(true);
        reply.setParentPost(parentPost);
        // reply.setCreatedAt(LocalDateTime.now());

        Post savedReply = postRepository.save(reply);

        // reply.getReplyPosts().add(savedReply);
        parentPost.getReplies().add(savedReply);
        Post savedParent = postRepository.save(parentPost);

        return savedParent;
    }

    @Override
    public List<Post> getUserPost(User user) {
        return postRepository.findByRepostUsersContainsOrUser_IdAndIsPostTrueOrderByCreatedAtDesc(user, user.getId());
    }

    @Override
    public List<Post> findByLikesContainsUser(User user) {
        return  postRepository.findByLikesUser_id(user.getId());
    }

}
