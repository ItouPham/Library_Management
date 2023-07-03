package com.api.Library_Management.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.BookResponse;
import com.api.Library_Management.model.response.book.ListBookResponse;

public interface BookService {

	ListBookResponse getAllBooks(int page, int size);

	ResponseEntity<?> getBookById(String id);

	ResponseEntity<?> createNewBook(BookRequest bookRequest) throws IOException ;

	ResponseEntity<?> editBook(String id, BookRequest bookRequest) throws IOException;

	ResponseEntity<?> deleteBook(String id);

	ResponseEntity<?>  getBooksByAuthorId(String id);

	ResponseEntity<?> getBooksByCategoryId(String id, int page, int size);
}
