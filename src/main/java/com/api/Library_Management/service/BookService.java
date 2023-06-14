package com.api.Library_Management.service;

import org.springframework.http.ResponseEntity;

import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.BookResponse;
import com.api.Library_Management.model.response.book.ListBookResponse;

public interface BookService {

	ListBookResponse getAllBooks(int page, int size);

	ResponseEntity<?> getBookById(String id);

	BookResponse createNewBook(BookRequest bookRequest);

	BookResponse editBook(String id, BookRequest bookRequest);

	BookResponse deleteBook(String id);

	ListBookResponse getBooksByAuthorId(String id);

	ResponseEntity<?> getBooksByCategoryId(String id, int page, int size);
}
