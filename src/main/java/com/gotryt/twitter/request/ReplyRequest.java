package com.gotryt.twitter.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReplyRequest {

    private String content;
    private Long postId;
    private LocalDateTime createdAt;
    private String image;
    private String video;

}
