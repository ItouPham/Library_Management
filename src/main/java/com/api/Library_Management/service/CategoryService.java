package com.api.Library_Management.service;

import com.api.Library_Management.model.request.CategoryRequest;
import com.api.Library_Management.model.response.category.CategoryResponse;
import com.api.Library_Management.model.response.category.ListCategoriesResponse;

public interface CategoryService {

	ListCategoriesResponse getAllCategories();

	CategoryResponse getCategoryById(String id);

	CategoryResponse createNewCategory(CategoryRequest authorRequest);

	CategoryResponse editCategory(String id, CategoryRequest authorRequest);

	CategoryResponse deleteCategory(String id);

}
