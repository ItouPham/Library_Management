package com.api.Library_Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.Library_Management.model.request.AuthorRequest;
import com.api.Library_Management.model.response.NotificationResponse;
import com.api.Library_Management.model.response.author.AuthorResponse;
import com.api.Library_Management.model.response.author.ListAuthorResponse;
import com.api.Library_Management.service.AuthorService;
import com.api.Library_Management.utils.Logs;

@RestController
@CrossOrigin
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping()
	public ResponseEntity<ListAuthorResponse> getAllAuthors() {
		ListAuthorResponse objAuthor = new ListAuthorResponse();
		try {
			objAuthor = authorService.getAllAuthors();
			return new ResponseEntity<ListAuthorResponse>(objAuthor, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objAuthor.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<ListAuthorResponse>(objAuthor, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable String id) {
		AuthorResponse objAuthor = new AuthorResponse();
		try {
			objAuthor = authorService.getAuthorById(id);
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objAuthor.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<AuthorResponse> createNewAuthor(@RequestBody AuthorRequest authorRequest) {
		AuthorResponse objAuthor = new AuthorResponse();
		try {
			objAuthor = authorService.createNewAuthor(authorRequest);
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objAuthor.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<AuthorResponse> editAuthor(@PathVariable String id,
			@RequestBody AuthorRequest authorRequest) {
		AuthorResponse objAuthor = new AuthorResponse();
		try {
			objAuthor = authorService.editAuthor(id, authorRequest);
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objAuthor.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AuthorResponse> deleteAuthor(@PathVariable String id) {
		AuthorResponse objAuthor = new AuthorResponse();
		try {
			objAuthor = authorService.deleteAuthor(id);
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			objAuthor.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return new ResponseEntity<AuthorResponse>(objAuthor, HttpStatus.OK);
		}
	}
}
