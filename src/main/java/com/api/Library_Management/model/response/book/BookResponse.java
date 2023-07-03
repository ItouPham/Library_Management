package com.api.Library_Management.model.response.book;

import com.api.Library_Management.entity.Book;

import lombok.Data;

@Data
public class BookResponse {
	private Book book;
	private String message;
}
