package com.api.Library_Management.controller;

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

import com.api.Library_Management.model.request.AuthorRequest;
import com.api.Library_Management.model.request.CategoryRequest;
import com.api.Library_Management.model.response.NotificationResponse;
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
	public ResponseEntity<ListCategoriesResponse> getAllCategories() {
		ListCategoriesResponse listCategories = new ListCategoriesResponse();
		try {
			listCategories = categoryService.getAllCategories();
			return new ResponseEntity<ListCategoriesResponse>(listCategories, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			listCategories.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<ListCategoriesResponse>(listCategories, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable String id) {
		CategoryResponse category = new CategoryResponse();
		try {
			category = categoryService.getCategoryById(id);
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			category.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<CategoryResponse> createNewCategory(@RequestBody CategoryRequest categoryRequest) {
		CategoryResponse category = new CategoryResponse();
		try {
			category = categoryService.createNewCategory(categoryRequest);
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			category.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);

		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponse> editCategory(@PathVariable String id,
			@RequestBody CategoryRequest categoryRequest) {
		CategoryResponse category = new CategoryResponse();
		try {
			category = categoryService.editCategory(id, categoryRequest);
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			category.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable String id) {
		CategoryResponse category = new CategoryResponse();
		try {
			category = categoryService.deleteCategory(id);
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			category.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<CategoryResponse>(category, HttpStatus.OK);
		}
	}
}
