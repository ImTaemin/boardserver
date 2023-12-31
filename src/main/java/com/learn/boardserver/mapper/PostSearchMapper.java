package com.learn.boardserver.mapper;

import com.learn.boardserver.dto.PostDTO;
import com.learn.boardserver.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
}

