package com.api.Library_Management.model.response.author;

import java.util.List;

import com.api.Library_Management.entity.Author;

import lombok.Data;

@Data
public class ListAuthorResponse {
	private List<Author> authors;
	private String message;
}
