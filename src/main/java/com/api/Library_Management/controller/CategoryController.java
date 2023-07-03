package com.api.Library_Management.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.Library_Management.model.request.CategoryRequest;
import com.api.Library_Management.model.response.category.CategoryResponse;
import com.api.Library_Management.model.response.category.ListCategoriesResponse;
import com.api.Library_Management.service.CategoryService;
import com.api.Library_Management.utils.Logs;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	public ResponseEntity<?> getAllCategories() {
		ListCategoriesResponse listCategories = categoryService.getAllCategories();
		return new ResponseEntity<ListCategoriesResponse>(listCategories, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable String id) {
		return categoryService.getCategoryById(id);
	}

	@PostMapping()
	public ResponseEntity<?> createNewCategory(@RequestBody CategoryRequest categoryRequest) {
		CategoryResponse categoryResponse = new CategoryResponse();
		if (StringUtils.isEmpty(categoryRequest.getName())) {
			categoryResponse.setMessage(Logs.NULL_CATEGORY_NAME.getMessage());
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return categoryService.createNewCategory(categoryRequest);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editCategory(@PathVariable String id, @RequestBody CategoryRequest categoryRequest) {
		CategoryResponse categoryResponse = new CategoryResponse();
		if (StringUtils.isEmpty(categoryRequest.getName())) {
			categoryResponse.setMessage(Logs.NULL_CATEGORY_NAME.getMessage());
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return categoryService.editCategory(id, categoryRequest);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable String id) {
		return categoryService.deleteCategory(id);
	}
}
