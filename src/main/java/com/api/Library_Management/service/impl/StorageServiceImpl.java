package com.api.Library_Management.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.Library_Management.entity.Book;
import com.api.Library_Management.exception.StorageException;
import com.api.Library_Management.exception.StorageFileNotFoundException;
import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService{
	private static final String BOOK_IMAGE_PATH_STR = "uploads/images";
	private static final Path BOOK_IMAGE_PATH = Paths.get(BOOK_IMAGE_PATH_STR);

	@Override
	public String getStoredFilename(MultipartFile file, String fileName) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + fileName + "." + ext;
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(BOOK_IMAGE_PATH);
		} catch (Exception e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public Path load(String fileName) {
		return BOOK_IMAGE_PATH.resolve(fileName);
	}

	@Override
	public Resource loadAsResource(String fileName) {
		try {
			Path file = load(fileName);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new StorageFileNotFoundException("Could not read file: " + fileName);
		} catch (Exception e) {
			throw new StorageFileNotFoundException("Could not read file: " + fileName);
		}
	}

	@Override
	public void store(MultipartFile file, String storedFileName) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file");
			}
			Path destinationFile = BOOK_IMAGE_PATH.resolve(Paths.get(storedFileName)).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(BOOK_IMAGE_PATH.toAbsolutePath())) {
				throw new StorageException("Cannot store file outside current directory");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("URL: " + destinationFile.toFile().toString());
			}
		} catch (Exception e) {
			throw new StorageException("Failed to store file", e);
		}
	}

	@Override
	public void delete(String fileName) throws IOException {
		Path destinationFile = BOOK_IMAGE_PATH.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
		Files.delete(destinationFile);
	}

	@Override
	public void saveBookImage(BookRequest bookRequest, Book book) {
		UUID uuid = UUID.randomUUID();
		String uuString = uuid.toString();
		book.setImage(getStoredFilename(bookRequest.getImage(), uuString));
		store(bookRequest.getImage(), book.getImage());
	}
}
