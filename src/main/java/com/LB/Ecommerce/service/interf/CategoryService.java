package com.LB.Ecommerce.service.interf;

import com.LB.Ecommerce.dto.CategoryDto;
import com.LB.Ecommerce.dto.Response;

public interface CategoryService {
    
    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);

}
