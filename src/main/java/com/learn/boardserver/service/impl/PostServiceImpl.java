package com.learn.boardserver.service.impl;

import com.learn.boardserver.dto.PostDTO;
import com.learn.boardserver.dto.UserDTO;
import com.learn.boardserver.mapper.PostMapper;
import com.learn.boardserver.mapper.UserProfileMapper;
import com.learn.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private UserProfileMapper userProfileMapper;
    private PostMapper postMapper;

    @Autowired
    public PostServiceImpl(UserProfileMapper userProfileMapper, PostMapper postMapper) {
        this.userProfileMapper = userProfileMapper;
        this.postMapper = postMapper;
    }

    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO userInfo = userProfileMapper.getUserProfile(id);
        if(userInfo == null) {
            log.error("register ERROR! {}", userInfo);
            throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요" + userInfo);
        }

        postDTO.setUserId(userInfo.getId());
        postDTO.setCreateTime(new Date());
        postMapper.register(postDTO);
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = postMapper.getMyPosts(accountId);

        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if(postDTO == null || postDTO.getId() <= 0) {
            log.error("updatePosts ERROR! {}", postDTO);
            throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요" + postDTO);
        }

        postMapper.updatePost(postDTO);
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if(userId <= 0 || postId <= 0) {
            log.error("deletePosts ERROR! {}", postId);
            throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요" + postId);
        }

        postMapper.deletePost(userId, postId);
    }
}
