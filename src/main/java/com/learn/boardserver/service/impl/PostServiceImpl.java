package com.learn.boardserver.service.impl;

import com.learn.boardserver.dto.CommentDTO;
import com.learn.boardserver.dto.PostDTO;
import com.learn.boardserver.dto.TagDTO;
import com.learn.boardserver.dto.UserDTO;
import com.learn.boardserver.mapper.CommentMapper;
import com.learn.boardserver.mapper.PostMapper;
import com.learn.boardserver.mapper.TagMapper;
import com.learn.boardserver.mapper.UserProfileMapper;
import com.learn.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private UserProfileMapper userProfileMapper;
    private PostMapper postMapper;
    private CommentMapper commentMapper;
    private TagMapper tagMapper;

    @Autowired
    public PostServiceImpl(UserProfileMapper userProfileMapper, PostMapper postMapper, CommentMapper commentMapper, TagMapper tagMapper) {
        this.userProfileMapper = userProfileMapper;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
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

        Integer postId = postDTO.getId();
        for(TagDTO tagDTO : postDTO.getTagDTOList()) {
            tagMapper.register(tagDTO);
            Integer tagId = tagDTO.getId();
            tagMapper.createPostTag(tagId, postId);
        }
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

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if(commentDTO.getPostId() <= 0) {
            log.error("registerComment ERROR! {}", commentDTO);
            throw new RuntimeException("registerComment ERROR! 댓글 등록 메서드를 확인해주세요" + commentDTO);
        }

        commentMapper.register(commentDTO);
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO == null) {
            log.error("updateComment ERROR!");
            throw new RuntimeException("updateComment ERROR! 댓글 수정 메서드를 확인해주세요");
        }

        commentMapper.update(commentDTO);
    }

    @Override
    public void deletePostComment(int userId, int commentId) {
        if(userId <= 0 || commentId <= 0) {
            log.error("deletePostComment ERROR! userId:{}, commentId:{}", userId, commentId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요 userId:" + userId + " commentId:" + commentId);
        }
        commentMapper.delete(commentId);
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO == null) {
            log.error("registerTag ERROR!");
            throw new RuntimeException("registerTag ERROR! 태그 등록 메서드를 확인해주세요");
        }

        tagMapper.register(tagDTO);
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO == null) {
            log.error("updateTag ERROR!");
            throw new RuntimeException("updateTag ERROR! 태그 수정 메서드를 확인해주세요");
        }

        tagMapper.update(tagDTO);
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if (userId <= 0 || tagId <= 0) {
            log.error("deletePostComment ERROR! userId:{}, tagId:{}", userId, tagId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요 userId:" + userId + " tagId:" + tagId);
        }

        tagMapper.delete(tagId);
    }
}
