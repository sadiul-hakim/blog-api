package com.blog.services;

import com.blog.payloads.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO dto);
    CategoryDTO updateCategory(CategoryDTO dto,Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryDTO getCategory(Integer categoryId);
    List<CategoryDTO> getAllCategory();

}
