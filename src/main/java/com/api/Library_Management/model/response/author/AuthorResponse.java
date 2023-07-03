package com.api.Library_Management.model.response.author;

import com.api.Library_Management.entity.Author;

import lombok.Data;

@Data
public class AuthorResponse {
	private Author author;
	private String message;
}
