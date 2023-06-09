package com.api.Library_Management.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.api.Library_Management.entity.Book;
import com.api.Library_Management.model.request.BookRequest;

public interface StorageService {
	void init();

	public String getStoredFilename(MultipartFile file, String id);

	Path load(String fileName);

	Resource loadAsResource(String storedFileName);

	void store(MultipartFile file, String storedFileName);

	void delete(String fileName) throws IOException;

	void saveBookImage(BookRequest bookRequest, Book book);
	
	Path getImage(String fileName);
}
