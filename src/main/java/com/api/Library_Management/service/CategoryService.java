package com.api.Library_Management.service;

import org.springframework.http.ResponseEntity;

import com.api.Library_Management.model.request.CategoryRequest;
import com.api.Library_Management.model.response.category.CategoryResponse;
import com.api.Library_Management.model.response.category.ListCategoriesResponse;

public interface CategoryService {

	ListCategoriesResponse getAllCategories();

	ResponseEntity<?> getCategoryById(String id);

	ResponseEntity<?> createNewCategory(CategoryRequest authorRequest);

	ResponseEntity<?> editCategory(String id, CategoryRequest authorRequest);

	ResponseEntity<?> deleteCategory(String id);

}
