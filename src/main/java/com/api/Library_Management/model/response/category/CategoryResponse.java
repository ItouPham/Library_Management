package com.api.Library_Management.model.response.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
	private ObjCategory category;
	private String message;
}
