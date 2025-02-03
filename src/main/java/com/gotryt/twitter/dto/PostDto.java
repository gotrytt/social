package com.gotryt.twitter.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

 @Data
public class PostDto {
    private Long id;
    private String content;
    private String image;
    private String Video;
    private UserDto user;
    private LocalDateTime createdAt;
    private int totalLikes;
    private int totalReplies;
    private int totalRepost;
    private boolean isLiked;
    private boolean isRepost;
    private List<Long> repostUserId;
    private List<PostDto> replies;
}
