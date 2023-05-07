package com.api.Library_Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.NotificationResponse;
import com.api.Library_Management.model.response.book.BookResponse;
import com.api.Library_Management.model.response.book.ListBookResponse;
import com.api.Library_Management.service.BookService;
import com.api.Library_Management.utils.Logs;

@RestController
@CrossOrigin
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<ListBookResponse> getAllBooks() {
		ListBookResponse objBooks = new ListBookResponse();
		try {
			objBooks = bookService.getAllBooks();
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBooks.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable String id) {
		BookResponse objBook = new BookResponse();
		try {
			objBook = bookService.getBookById(id);
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBook.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		}
	}

	@PostMapping
	public ResponseEntity<BookResponse> createNewBook(@ModelAttribute BookRequest bookRequest) {
		BookResponse objBook = new BookResponse();
		try {
			objBook = bookService.createNewBook(bookRequest);
			return new ResponseEntity<BookResponse>(objBook, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBook.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
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
			objBook.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
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
			objBook.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
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
			objBooks.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		}
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<ListBookResponse> getBooksByCategoryId(@PathVariable String id) {
		ListBookResponse objBooks = new ListBookResponse();
		try {
			objBooks = bookService.getBooksByCategoryId(id);
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objBooks.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<ListBookResponse>(objBooks, HttpStatus.OK);
		}
	}
}
