package com.api.Library_Management.model.response.category;

import com.api.Library_Management.entity.Category;

import lombok.Data;

@Data
public class CategoryResponse {
	private Category category;
	private String message;
}
