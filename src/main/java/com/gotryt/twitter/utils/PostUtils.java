package com.gotryt.twitter.utils;

import com.gotryt.twitter.model.Like;
import com.gotryt.twitter.model.Post;
import com.gotryt.twitter.model.User;

public class PostUtils {

    public final static boolean isLikedByReqUser(User reqUser, Post post) {

        for (Like like : post.getLikes()) {
            if (like.getUser().getId() == (reqUser.getId())) {
                return true;
            }
        }
        return false;
    }
    public final static boolean isRepostedByReqUser(User reqUser, Post post) {
        for (User user : post.getRepostUsers()) {
            if (user.getId() == (reqUser.getId())) {
                return true;
            }
        }
        return false;
    }
}
