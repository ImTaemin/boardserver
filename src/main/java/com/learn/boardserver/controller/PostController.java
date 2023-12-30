package com.learn.boardserver.controller;

import com.learn.boardserver.aop.LoginCheck;
import com.learn.boardserver.dto.PostDTO;
import com.learn.boardserver.dto.UserDTO;
import com.learn.boardserver.dto.response.CommonResponse;
import com.learn.boardserver.service.impl.PostServiceImpl;
import com.learn.boardserver.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private UserServiceImpl userService;
    private PostServiceImpl postService;

    @Autowired
    public PostController(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId, @RequestBody PostDTO postDTO) {
        postService.register(accountId, postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "registerPost", postDTO);

        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyPosts(userInfo.getId());
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "myPostInfo", postDTOList);

        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePosts(String accountId,
                                                                    @PathVariable("postId") int postId,
                                                                    @RequestBody PostRequest postRequest) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(userInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();

        postService.updatePosts(postDTO);

        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "updatePosts", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<Integer>> deletePosts(String accountId,
                                                                         @PathVariable("postId") int postId) {
        UserDTO userInfo = userService.getUserInfo(accountId);
        postService.deletePosts(userInfo.getId(), postId);

        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePosts", postId);
        return ResponseEntity.ok(commonResponse);
    }

    // -- response 객체 --
    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTOList;
    }

    // -- request 객체 --
    @Getter
    @Setter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

}
