package com.api.Library_Management.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.api.Library_Management.entity.Author;
import com.api.Library_Management.entity.Book;
import com.api.Library_Management.entity.Category;
import com.api.Library_Management.model.request.AuthorRequest;
import com.api.Library_Management.model.request.CategoryRequest;
import com.api.Library_Management.model.response.NotificationResponse;
import com.api.Library_Management.model.response.author.AuthorResponse;
import com.api.Library_Management.model.response.author.ListAuthorResponse;
import com.api.Library_Management.model.response.author.ObjAuthor;
import com.api.Library_Management.model.response.category.CategoryResponse;
import com.api.Library_Management.model.response.category.ListCategoriesResponse;
import com.api.Library_Management.model.response.category.ObjCategory;
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
		List<ObjCategory> listCategoriesResponse = new ArrayList<>();
		List<Category> listCategories = new ArrayList<>();
		try {
			listCategories = categoryRepository.findAll();
			if (listCategories != null && listCategories.size() > 0) {
				for (Category category : listCategories) {
					ObjCategory objCategory = new ObjCategory();
					BeanUtils.copyProperties(category, objCategory);
					listCategoriesResponse.add(objCategory);
				}
				categoryResponse.setCategories(listCategoriesResponse);
			}
			categoryResponse.setNotification(new NotificationResponse(Logs.GET_DATA_SUCCESS.getMessage()));
			return categoryResponse;
		} catch (Exception e) {
			e.printStackTrace();
			categoryResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return categoryResponse;
		}
	}

	@Override
	public CategoryResponse getCategoryById(String id) {
		CategoryResponse categoryResponse = new CategoryResponse();
		ObjCategory objCategory = new ObjCategory();
		Category category = new Category();
		try {
			category = categoryRepository.findById(id).orElse(null);
			if (category != null) {
				BeanUtils.copyProperties(category, objCategory);
				categoryResponse.setCategory(objCategory);
				categoryResponse.setNotification(new NotificationResponse(Logs.GET_DATA_SUCCESS.getMessage()));
			} else {
				categoryResponse.setNotification(new NotificationResponse(Logs.CATEGORY_NOT_EXIST.getMessage()));
			}
			return categoryResponse;
		} catch (Exception e) {
			e.printStackTrace();
			categoryResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return categoryResponse;
		}
	}

	@Override
	@Transactional
	public CategoryResponse createNewCategory(CategoryRequest authorRequest) {
		CategoryResponse categoryResponse = new CategoryResponse();
		ObjCategory objCategory = new ObjCategory();
		try {
			Category category = new Category();
			BeanUtils.copyProperties(authorRequest, category);
			categoryRepository.save(category);
			BeanUtils.copyProperties(category, objCategory);

			categoryResponse.setCategory(objCategory);
			categoryResponse.setNotification(new NotificationResponse(Logs.ADD_CATEGORY_SUCCESS.getMessage()));
			return categoryResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			categoryResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return categoryResponse;
		}
	}

	@Override
	@Transactional
	public CategoryResponse editCategory(String id, CategoryRequest authorRequest) {
		CategoryResponse categoryResponse = new CategoryResponse();
		ObjCategory objCategory = new ObjCategory();
		Category category = new Category();
		try {
			category = categoryRepository.findById(id).orElse(null);
			if (category != null) {
				BeanUtils.copyProperties(authorRequest, category);
				BeanUtils.copyProperties(category, objCategory);
				categoryRepository.save(category);
				categoryResponse.setCategory(objCategory);
				categoryResponse.setNotification(new NotificationResponse(Logs.UPDATE_CATEGORY_SUCCESS.getMessage()));
			} else {
				categoryResponse.setNotification(new NotificationResponse(Logs.CATEGORY_NOT_EXIST.getMessage()));
			}
			return categoryResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			categoryResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return categoryResponse;
		}
	}

	@Override
	@Transactional
	public CategoryResponse deleteCategory(String id) {
		CategoryResponse categoryResponse = new CategoryResponse();
		ObjCategory objCategory = new ObjCategory();
		Category category = new Category();
		try {
			category = categoryRepository.findById(id).orElse(null);
			if (category != null) {
				List<Book> books = bookRepository.findByCategories(category);
				if (books != null) {
					for (Book book : books) {
						book.getCategories().removeIf(c -> c.getId().equals(id));
					}
					bookRepository.saveAll(books);
				}
				BeanUtils.copyProperties(category, objCategory);
				categoryRepository.delete(category);
				categoryResponse.setCategory(objCategory);
				categoryResponse.setNotification(new NotificationResponse(Logs.DELETE_CATEGORY_SUCCESS.getMessage()));
			} else {
				categoryResponse.setNotification(new NotificationResponse(Logs.CATEGORY_NOT_EXIST.getMessage()));
			}
			return categoryResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			categoryResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return categoryResponse;

		}
	}

}
