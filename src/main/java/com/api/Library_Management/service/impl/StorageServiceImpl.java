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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.api.Library_Management.entity.Book;
import com.api.Library_Management.exception.StorageException;
import com.api.Library_Management.exception.StorageFileNotFoundException;
import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.ObjBookImage;
import com.api.Library_Management.service.StorageService;
import com.api.Library_Management.utils.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StorageServiceImpl implements StorageService{
//	private static final String BOOK_IMAGE_PATH_STR = "uploads/images";
//	@Value("${book.image.path}")
//	public String BOOK_IMAGE_PATH_STR;
//	private final Path BOOK_IMAGE_PATH = Paths.get(BOOK_IMAGE_PATH_STR);

	@Override
	public String getStoredFilename(MultipartFile file, String fileName) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + fileName + "." + ext;
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(Paths.get(ConfigReader.BOOK_IMAGE_PATH_STR));
		} catch (Exception e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public Path load(String fileName) {
		return Paths.get(ConfigReader.BOOK_IMAGE_PATH_STR).resolve(fileName);
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
			Path destinationFile = Paths.get(ConfigReader.BOOK_IMAGE_PATH_STR).resolve(Paths.get(storedFileName)).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(Paths.get(ConfigReader.BOOK_IMAGE_PATH_STR).toAbsolutePath())) {
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
		Path destinationFile = Paths.get(ConfigReader.BOOK_IMAGE_PATH_STR).resolve(Paths.get(fileName)).normalize().toAbsolutePath();
		Files.delete(destinationFile);
	}

	@Override
	public void saveBookImage(BookRequest bookRequest, Book book) {
		UUID uuid = UUID.randomUUID();
		String uuString = uuid.toString();
//		book.setImage(getStoredFilename(bookRequest.getImage(), uuString));
//		store(bookRequest.getImage(), book.getImage());
	}

	@Override
	public Path getImage(String fileName) {
		Path filePath = Paths.get(ConfigReader.BOOK_IMAGE_PATH_STR).resolve(fileName);
		return filePath;
	}

	@Override
	public ObjBookImage postImageToImgur(MultipartFile file, BookRequest request) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		RestTemplate restTemplate = new RestTemplate();
		String url = ConfigReader.POST_BOOK_IMAGE_URL;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		httpHeaders.set("Authorization", ConfigReader.AUTHORIZATION_TOKEN);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("image", file.getBytes());
		body.add("album", ConfigReader.ALBUM_ID);
		body.add("name", request.getName());
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, httpHeaders);
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		ObjBookImage bookImageResponse = objectMapper.readValue(response.getBody(), ObjBookImage.class);
		return bookImageResponse;
	}

	@Override
	public ResponseEntity<String> deleteImageFromImgur(String deletehash) {
		RestTemplate restTemplate = new RestTemplate();
		String url = ConfigReader.DELETE_BOOK_IMAGE_URL + deletehash;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", ConfigReader.AUTHORIZATION_TOKEN);
		HttpEntity<Object> entity = new HttpEntity<Object>(httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE , entity, String.class);
		return response;
	}
}
