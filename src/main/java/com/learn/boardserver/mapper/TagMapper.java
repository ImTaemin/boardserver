package com.learn.boardserver.mapper;

import com.learn.boardserver.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    int register(TagDTO commentDTO);

    void update(TagDTO commentDTO);

    void delete(int tagId);

    void createPostTag(Integer tagId, Integer postId);
}
