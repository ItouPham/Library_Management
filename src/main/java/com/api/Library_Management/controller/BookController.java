package com.api.Library_Management.controller;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.BookResponse;
import com.api.Library_Management.model.response.book.ListBookResponse;
import com.api.Library_Management.service.BookService;
import com.api.Library_Management.service.StorageService;
import com.api.Library_Management.utils.ConfigReader;
import com.api.Library_Management.utils.Logs;

@RestController
@CrossOrigin
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired
	private StorageService storageService;

	@GetMapping
	public ResponseEntity<ListBookResponse> getAllBooks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		ListBookResponse objBooks = new ListBookResponse();
		try {
			objBooks = bookService.getAllBooks(page, size);
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBooks.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		}
	}

	@GetMapping("getById/{id}")
	public ResponseEntity<?> getBookById(@PathVariable String id) {
		return bookService.getBookById(id);
	}

	@PostMapping
	public ResponseEntity<BookResponse> createNewBook(@ModelAttribute BookRequest bookRequest) {
		BookResponse objBook = new BookResponse();
		try {
			objBook = bookService.createNewBook(bookRequest);
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBook.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookResponse> editBook(@PathVariable String id, @ModelAttribute BookRequest bookRequest) {
		BookResponse objBook = new BookResponse();
		try {
			objBook = bookService.editBook(id, bookRequest);
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBook.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BookResponse> deleteBook(@PathVariable String id) {
		BookResponse objBook = new BookResponse();
		try {
			objBook = bookService.deleteBook(id);
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBook.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		}
	}

	@GetMapping("/author/{id}")
	public ResponseEntity<ListBookResponse> getBooksByAuthorId(@PathVariable String id) {
		ListBookResponse objBooks = new ListBookResponse();
		try {
			objBooks = bookService.getBooksByAuthorId(id);
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBooks.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		}
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<?> getBooksByCategoryId(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @PathVariable String id) {
		ListBookResponse objBooks = new ListBookResponse();
		try {
			return bookService.getBooksByCategoryId(id,page,size);
		} catch (Exception e) {
			e.printStackTrace();
			objBooks.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("bookImage/{fileName}")
	public ResponseEntity<?> getBookImage(@PathVariable String fileName) throws IOException {
		byte[] imageData = Files.readAllBytes(storageService.getImage(fileName));
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
	}

	@PostMapping("test")
	public ResponseEntity<?> test(@RequestParam String deletehash) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
		String url = ConfigReader.DELETE_BOOK_IMAGE_URL + deletehash;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", ConfigReader.AUTHORIZATION_TOKEN);
		HttpEntity<Object> entity = new HttpEntity<Object>(httpHeaders);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		return response;
	}
}
