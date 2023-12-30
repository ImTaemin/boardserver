package com.learn.boardserver.mapper;

import com.learn.boardserver.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    int register(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(int categoryId);
}
