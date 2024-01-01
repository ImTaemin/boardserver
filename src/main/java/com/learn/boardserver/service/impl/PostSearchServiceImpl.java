package com.learn.boardserver.service.impl;

import com.learn.boardserver.dto.PostDTO;
import com.learn.boardserver.dto.request.PostSearchRequest;
import com.learn.boardserver.exception.BoardServerException;
import com.learn.boardserver.mapper.PostSearchMapper;
import com.learn.boardserver.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    private PostSearchMapper postSearchMapper;

    @Autowired
    public PostSearchServiceImpl(PostSearchMapper postSearchMapper) {
        this.postSearchMapper = postSearchMapper;
    }

    @Async
    @Cacheable(value = "getPosts", key="'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("getPosts 메서드 실패", e.getMessage());
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostByTagName(String tagName) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPostByTagName(tagName);
        } catch (RuntimeException e) {
            log.error("getPosts 메서드 실패", e.getMessage());
        }

        return postDTOList;
    }
}
