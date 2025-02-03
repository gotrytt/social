package com.gotryt.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gotryt.twitter.dto.PostDto;
import com.gotryt.twitter.dto.mapper.PostDtoMapper;
import com.gotryt.twitter.exception.PostException;
import com.gotryt.twitter.exception.UserException;
import com.gotryt.twitter.model.Post;
import com.gotryt.twitter.model.User;
import com.gotryt.twitter.request.ReplyRequest;
import com.gotryt.twitter.response.ApiResponse;
import com.gotryt.twitter.service.PostService;
import com.gotryt.twitter.service.UserService;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAllPost(@RequestHeader("Authorization") String jwt) throws PostException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        List<Post> posts = postService.findAllPost();

        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);
        
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody Post request, @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createPost(request, user);

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        
        return new ResponseEntity<>(postDto,HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<PostDto> replyPost(@RequestBody ReplyRequest request, @RequestHeader("Authorization") String jwt) throws UserException, PostException {

        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.createReply(request, user);

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        
        return new ResponseEntity<>(postDto,HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/repost")
    public ResponseEntity<PostDto> repost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws UserException, PostException {

        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.repost(postId, user);

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPostById(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws UserException, PostException {

        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.findById(postId);

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws UserException, PostException {

        User user = userService.findUserProfileByJwt(jwt);
        postService.deletePostById(postId, user.getId());

        ApiResponse response = new ApiResponse();
        response.setMessage("post deleted successfully");
        response.setStatus(true);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> findUserAllPost(@RequestHeader("Authorization") String jwt) throws PostException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        List<Post> posts = postService.getUserPost(user);

        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);
        
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/user/{UserId}/likes")
    public ResponseEntity<List<PostDto>> findLikedPost(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws PostException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        List<Post> posts = postService.findByLikesContainsUser(user);

        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);
        
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }    

}
