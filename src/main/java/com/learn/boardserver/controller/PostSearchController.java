package com.learn.boardserver.controller;

import com.learn.boardserver.dto.PostDTO;
import com.learn.boardserver.dto.request.PostSearchRequest;
import com.learn.boardserver.service.impl.PostSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = postSearchService.getPosts(postSearchRequest);
        return new PostSearchResponse(postDTOList);
    }

    @GetMapping
    public PostSearchResponse searchByTagName(String tagName) {
        List<PostDTO> postDTOList = postSearchService.getPostByTagName(tagName);
        return new PostSearchResponse(postDTOList);
    }

    // -- response 객체 --

    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDTO> postDTOList;
    }
}
