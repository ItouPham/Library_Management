package com.api.Library_Management.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.Library_Management.entity.Category;
import com.api.Library_Management.model.request.CategoryRequest;
import com.api.Library_Management.model.response.category.CategoryResponse;
import com.api.Library_Management.model.response.category.ListCategoriesResponse;
import com.api.Library_Management.repository.BookRepository;
import com.api.Library_Management.repository.CategoryRepository;
import com.api.Library_Management.service.CategoryService;
import com.api.Library_Management.utils.Logs;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BookRepository bookRepository;

	@Override
	public ListCategoriesResponse getAllCategories() {
		ListCategoriesResponse categoryResponse = new ListCategoriesResponse();
		List<Category> listCategories = categoryRepository.findAll();
		categoryResponse.setCategories(listCategories);
		categoryResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
		return categoryResponse;
	}

	@Override
	public ResponseEntity<?> getCategoryById(String id) {
		CategoryResponse categoryResponse = new CategoryResponse();
		Category category = categoryRepository.findById(id).orElse(null);
		if (category != null) {
			categoryResponse.setCategory(category);
			categoryResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.OK);
		} else {
			String message = Logs.CATEGORY_NOT_EXIST.getMessage().replace("%ID%", id);
			categoryResponse.setMessage(message);
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> createNewCategory(CategoryRequest authorRequest) {
		CategoryResponse categoryResponse = new CategoryResponse();
		Category category = categoryRepository.findByName(authorRequest.getName()).orElse(null);
		if (category != null) {
			categoryResponse.setMessage(Logs.CATEGORY_HAS_EXISTED.getMessage());
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.CONFLICT);
		} else {
			Category savedCategory = new Category();
			BeanUtils.copyProperties(authorRequest, savedCategory);
			savedCategory = categoryRepository.save(savedCategory);
			if (savedCategory	 != null) {
				categoryResponse.setCategory(savedCategory);
				categoryResponse.setMessage(Logs.ADD_CATEGORY_SUCCESS.getMessage());
				return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.CREATED);
			} else {
				categoryResponse.setMessage(Logs.ADD_CATEGORY_UNSUCCESS.getMessage());
				return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> editCategory(String id, CategoryRequest authorRequest) {
		CategoryResponse categoryResponse = new CategoryResponse();
		Category category = categoryRepository.findById(id).orElse(null);
		if (category != null) {
			BeanUtils.copyProperties(authorRequest, category);
			category = categoryRepository.save(category);
			if (category != null) {
				categoryResponse.setCategory(category);
				categoryResponse.setMessage(Logs.UPDATE_CATEGORY_SUCCESS.getMessage());
				return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.OK);
			} else {
				categoryResponse.setMessage(Logs.UPDATE_CATEGORY_UNSUCCESS.getMessage());
				return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			String message = Logs.CATEGORY_NOT_EXIST.getMessage().replace("%ID%", id);
			categoryResponse.setMessage(message);
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteCategory(String id) {
		CategoryResponse categoryResponse = new CategoryResponse();
		Category category = categoryRepository.findById(id).orElse(null);
		if (category != null) {
			categoryRepository.delete(category);
			categoryResponse.setCategory(null);
			categoryResponse.setMessage(Logs.DELETE_CATEGORY_SUCCESS.getMessage());
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.OK);
		} else {
			String message = Logs.CATEGORY_NOT_EXIST.getMessage().replace("%ID%", id);
			categoryResponse.setMessage(message);
			return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.NO_CONTENT);
		}
	}

}
