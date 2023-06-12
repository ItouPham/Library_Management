package com.api.Library_Management.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.api.Library_Management.entity.Book;
import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.ObjBookImage;

public interface StorageService {
	void init();

	public String getStoredFilename(MultipartFile file, String id);

	Path load(String fileName);

	Resource loadAsResource(String storedFileName);

	void store(MultipartFile file, String storedFileName);

	void delete(String fileName) throws IOException;

	void saveBookImage(BookRequest bookRequest, Book book);
	
	Path getImage(String fileName);
	
	ObjBookImage postImageToImgur(MultipartFile file, BookRequest request) throws IOException ;
	
	ResponseEntity<String> deleteImageFromImgur(String deletehash);
}
