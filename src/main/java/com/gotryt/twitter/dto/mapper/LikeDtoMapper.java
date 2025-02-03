package com.gotryt.twitter.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.gotryt.twitter.dto.LikeDto;
import com.gotryt.twitter.dto.PostDto;
import com.gotryt.twitter.dto.UserDto;
import com.gotryt.twitter.model.Like;
import com.gotryt.twitter.model.User;

public class LikeDtoMapper {

    public static LikeDto toLikeDto(Like like, User reqUser) {
        
        UserDto user = UserDtoMapper.toUserDto(like.getUser());
        // UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);
        PostDto post = PostDtoMapper.toPostDto(like.getPost(), reqUser);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setPost(post);
        likeDto.setUser(user);

        return likeDto;
    }

    public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser) {
        List<LikeDto> likeDtos = new ArrayList<>();

        for (Like like : likes) {
            UserDto user = UserDtoMapper.toUserDto(like.getUser());
            PostDto post = PostDtoMapper.toPostDto(like.getPost(), reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setPost(post);
            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }

        return likeDtos;
    }
}
