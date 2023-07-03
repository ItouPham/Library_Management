package com.api.Library_Management.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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

import com.api.Library_Management.model.request.AuthorRequest;
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
		ListAuthorResponse objAuthor = authorService.getAllAuthors();
		return new ResponseEntity<ListAuthorResponse>(objAuthor, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAuthorById(@PathVariable String id) {
		return authorService.getAuthorById(id);
	}

	@PostMapping()
	public ResponseEntity<?> createNewAuthor(@ModelAttribute AuthorRequest authorRequest) throws IOException {
		AuthorResponse authorResponse = new AuthorResponse();
		if(StringUtils.isEmpty(authorRequest.getName())){
			authorResponse.setMessage(Logs.NULL_AUTHOR_NAME.getMessage());
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.BAD_REQUEST);
		}
		return authorService.createNewAuthor(authorRequest);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editAuthor(@PathVariable String id, @ModelAttribute AuthorRequest authorRequest) throws IOException {
		AuthorResponse authorResponse = new AuthorResponse();
		if (StringUtils.isEmpty(authorRequest.getName())) {
			authorResponse.setMessage(Logs.NULL_AUTHOR_NAME.getMessage());
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.BAD_REQUEST);
		}
		return authorService.editAuthor(id, authorRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAuthor(@PathVariable String id) {
			return authorService.deleteAuthor(id);
	}
}
