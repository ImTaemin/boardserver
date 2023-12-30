package com.learn.boardserver.mapper;

import com.learn.boardserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    int register(PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePost(PostDTO postDTO);

    void deletePost(int accountId, int postId);
}
