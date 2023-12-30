package com.learn.boardserver.service;

import com.learn.boardserver.dto.CategoryDTO;

public interface CategoryService {
    void register(String accountId, CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(int categoryId);
}
