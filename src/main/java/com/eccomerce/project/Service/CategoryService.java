package com.eccomerce.project.Service;

import com.eccomerce.project.payload.CategoryDTO;
import com.eccomerce.project.payload.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CategoryResponse getAllCatgory(Integer pageNumber, Integer pageSize, String sortBy , String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDto);
    String deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId);

}
