package com.learn.boardserver.service.impl;

import com.learn.boardserver.dto.CategoryDTO;
import com.learn.boardserver.mapper.CategoryMapper;
import com.learn.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if(accountId == null) {
            log.error("register ERROR! {}", categoryDTO);
            throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메서드를 확인해주세요" + categoryDTO);
        }

        categoryMapper.register(categoryDTO);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            log.error("update ERROR! {}", categoryDTO);
            throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryDTO);
        }

        categoryMapper.updateCategory(categoryDTO);

    }

    @Override
    public void delete(int categoryId) {
        if(categoryId <= 0) {
            log.error("delete ERROR! {}", categoryId);
            throw new RuntimeException("delete ERROR! 게시글 카테고리 삭제 메서드를 확인해주세요" + categoryId);
        }

        categoryMapper.deleteCategory(categoryId);
    }
}
