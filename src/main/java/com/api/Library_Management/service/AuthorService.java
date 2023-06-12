package com.api.Library_Management.service;

import com.api.Library_Management.model.request.AuthorRequest;
import com.api.Library_Management.model.response.author.AuthorResponse;
import com.api.Library_Management.model.response.author.ListAuthorResponse;

public interface AuthorService {

	ListAuthorResponse getAllAuthors();

	AuthorResponse getAuthorById(String id);

	AuthorResponse createNewAuthor(AuthorRequest authorRequest);

	AuthorResponse editAuthor(String id, AuthorRequest authorRequest);

	AuthorResponse deleteAuthor(String id);

}
