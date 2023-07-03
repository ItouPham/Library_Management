package com.api.Library_Management.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.api.Library_Management.model.request.AuthorRequest;
import com.api.Library_Management.model.response.author.AuthorResponse;
import com.api.Library_Management.model.response.author.ListAuthorResponse;

public interface AuthorService {

	ListAuthorResponse getAllAuthors();

	ResponseEntity<?> getAuthorById(String id);

	ResponseEntity<?> createNewAuthor(AuthorRequest authorRequest) throws IOException;

	ResponseEntity<?> editAuthor(String id, AuthorRequest authorRequest) throws IOException;

	ResponseEntity<?> deleteAuthor(String id);

}
