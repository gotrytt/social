package com.gotryt.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gotryt.twitter.model.Post;
import com.gotryt.twitter.model.User;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByIsPostTrueOrderByCreatedAtDesc();

    List<Post> findByRepostUsersContainsOrUser_IdAndIsPostTrueOrderByCreatedAtDesc(User user, Long userId);

    List<Post> findByLikesContainingOrderByCreatedAtDesc(User user);

    @Query("SELECT p FROM Post p Join p.likes l WHERE l.user.id = :userId")
    List<Post> findByLikesUser_id(Long userId);

}
