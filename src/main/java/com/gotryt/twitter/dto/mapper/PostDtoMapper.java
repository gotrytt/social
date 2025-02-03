package com.gotryt.twitter.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.gotryt.twitter.dto.PostDto;
import com.gotryt.twitter.dto.UserDto;
import com.gotryt.twitter.model.Post;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.utils.PostUtils;

public class PostDtoMapper {

    public static PostDto toPostDto(Post post, User reqUser) {

        UserDto user = UserDtoMapper.toUserDto(post.getUser());
        boolean isLiked = PostUtils.isLikedByReqUser(reqUser, post);
        boolean isReposted = PostUtils.isRepostedByReqUser(reqUser, post);
        List<Long> repostUserId = new ArrayList<>();

        for (User user1 : post.getRepostUsers()) {
            repostUserId.add(user1.getId());
        }

        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setImage(post.getImage());
        postDto.setTotalLikes(post.getLikes().size());
        postDto.setTotalReplies(post.getReplies().size());
        postDto.setTotalRepost(post.getRepostUsers().size());
        postDto.setUser(user);
        postDto.setLiked(isLiked);
        postDto.setRepost(isReposted);
        postDto.setRepostUserId(repostUserId);
        postDto.setReplies(toPostDtos(post.getReplies(), reqUser));
        postDto.setVideo(post.getVideo());

        return postDto;
    }

    public static List<PostDto> toPostDtos(List<Post> posts, User reqUser) {
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            PostDto postDto = toRelyPostDto(post, reqUser);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    private static PostDto toRelyPostDto(Post post, User reqUser) {

        UserDto user = UserDtoMapper.toUserDto(post.getUser());
        boolean isLiked = PostUtils.isLikedByReqUser(reqUser, post);
        boolean isReposted = PostUtils.isRepostedByReqUser(reqUser, post);
        List<Long> repostUserId = new ArrayList<>();

        for (User user1 : post.getRepostUsers()) {
            repostUserId.add(user1.getId());
        }

        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setImage(post.getImage());
        postDto.setTotalLikes(post.getLikes().size());
        postDto.setTotalReplies(post.getReplies().size());
        postDto.setTotalRepost(post.getRepostUsers().size());
        postDto.setUser(user);
        postDto.setLiked(isLiked);
        postDto.setRepost(isReposted);
        postDto.setRepostUserId(repostUserId);
        // postDto.setReplyPost(toPostDtos(post.getReplyPosts(), reqUser));
        postDto.setVideo(post.getVideo());

        return postDto;
    }

}
