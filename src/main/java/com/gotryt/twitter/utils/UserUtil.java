package com.gotryt.twitter.utils;

import com.gotryt.twitter.model.User;

public class UserUtil {

    public static final boolean isReq_user(User reqUser, User user2) {
        return reqUser.getId() == (user2.getId());
    }

    public static final boolean isFollowedByReqUser(User reqUser, User user2) {
        return reqUser.getFollowing().contains(user2);
    }
}
