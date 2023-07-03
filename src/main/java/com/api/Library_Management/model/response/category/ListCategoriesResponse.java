package com.api.Library_Management.model.response.category;

import java.util.List;

import com.api.Library_Management.entity.Category;

import lombok.Data;

@Data
public class ListCategoriesResponse {
	private List<Category> categories;
	private String message;
}
