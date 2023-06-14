package com.api.Library_Management.model.response.book;

import java.util.List;

import com.api.Library_Management.entity.Category;

import lombok.Data;

@Data
public class ListBookResponse {
	private List<ObjBook> books;
	private List<Category> categories;
	private int currentPage;
	private long totalItems;
	private int totalPages;
	private String message;

}
