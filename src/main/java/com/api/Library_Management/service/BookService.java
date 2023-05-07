package com.api.Library_Management.service;

import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.BookResponse;
import com.api.Library_Management.model.response.book.ListBookResponse;

public interface BookService {

	ListBookResponse getAllBooks();

	BookResponse getBookById(String id);

	BookResponse createNewBook(BookRequest bookRequest);

	BookResponse editBook(String id, BookRequest bookRequest);

	BookResponse deleteBook(String id);

	ListBookResponse getBooksByAuthorId(String id);

	ListBookResponse getBooksByCategoryId(String id);
}
