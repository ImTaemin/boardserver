package com.learn.boardserver.mapper;

import com.learn.boardserver.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    int register(CommentDTO commentDTO);

    void update(CommentDTO commentDTO);

    void delete(int commentId);

}
