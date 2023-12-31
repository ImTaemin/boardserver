package com.learn.boardserver.service;

import com.learn.boardserver.dto.PostDTO;
import com.learn.boardserver.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {
    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);

    List<PostDTO> getPostByTagName(String tagName);
}
