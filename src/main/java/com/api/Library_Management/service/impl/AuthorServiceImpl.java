package com.api.Library_Management.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.Library_Management.entity.Author;
import com.api.Library_Management.model.bean.ObjBeanImage;
import com.api.Library_Management.model.request.AuthorRequest;
import com.api.Library_Management.model.response.author.AuthorResponse;
import com.api.Library_Management.model.response.author.ListAuthorResponse;
import com.api.Library_Management.model.response.image.ObjImage;
import com.api.Library_Management.repository.AuthorRepository;
import com.api.Library_Management.repository.BookRepository;
import com.api.Library_Management.service.AuthorService;
import com.api.Library_Management.service.StorageService;
import com.api.Library_Management.utils.Logs;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private StorageService storageService;

	@Override
	public ListAuthorResponse getAllAuthors() {
		List<Author> listAuthors = authorRepository.findAll();
		ListAuthorResponse authorResponse = new ListAuthorResponse();
		authorResponse.setAuthors(listAuthors);
		authorResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
		return authorResponse;
	}

	@Override
	public ResponseEntity<?> getAuthorById(String id) {
		AuthorResponse authorResponse = new AuthorResponse();
		Author author = authorRepository.findById(id).orElse(null);
		if (author != null) {
			authorResponse.setAuthor(author);
			authorResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.OK);
		} else {
			String message = Logs.AUTHOR_NOT_EXIST.getMessage().replace("%ID%", id);
			authorResponse.setMessage(message);
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> createNewAuthor(AuthorRequest authorRequest) throws IOException {
		AuthorResponse authorResponse = new AuthorResponse();
		Author author = authorRepository.findByName(authorRequest.getName()).orElse(null);
		if (author != null) {
			authorResponse.setMessage(Logs.AUTHOR_HAS_EXISTED.getMessage());
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.CONFLICT);
		} else {
			Author savedAuthor = new Author();
			BeanUtils.copyProperties(authorRequest, savedAuthor);
			if (!authorRequest.getAvatar().isEmpty()) {
				ObjImage objImage = storageService.postImageToImgur(authorRequest.getAvatar(), authorRequest.getName(),
						"AUTHOR");
				if (objImage.getStatus() != 200) {
					authorResponse.setMessage(Logs.UPLOAD_IMAGE_UNSUCCESS.getMessage());
					return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				ObjBeanImage imageResponse = storageService.responseToEntity(objImage.getData());
				savedAuthor.setAvatar(imageResponse);
			}
			savedAuthor = authorRepository.save(savedAuthor);
			if (savedAuthor != null) {
				authorResponse.setAuthor(savedAuthor);
				authorResponse.setMessage(Logs.ADD_AUTHOR_SUCCESS.getMessage());
				return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.CREATED);
			} else {
				authorResponse.setMessage(Logs.ADD_AUTHOR_UNSUCCESS.getMessage());
				return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> editAuthor(String id, AuthorRequest authorRequest) throws IOException {
		AuthorResponse authorResponse = new AuthorResponse();
		Author author = authorRepository.findById(id).orElse(null);
		if (author != null) {
			BeanUtils.copyProperties(authorRequest, author);
			if (!authorRequest.getAvatar().isEmpty()) {
				ResponseEntity<String> deleteImageResponse = storageService
						.deleteImageFromImgur(author.getAvatar().getDeletehash());
				if (deleteImageResponse.getStatusCodeValue() != 200) {
					authorResponse.setMessage(Logs.DELETE_IMAGE_UNSUCCESS.getMessage());
					return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				ObjImage objBookImage = storageService.postImageToImgur(authorRequest.getAvatar(),
						authorRequest.getName(), "AUTHOR");
				if (objBookImage.getStatus() != 200) {
					authorResponse.setMessage(Logs.UPLOAD_IMAGE_UNSUCCESS.getMessage());
					return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				ObjBeanImage bookImageResponse = storageService.responseToEntity(objBookImage.getData());
				author.setAvatar(bookImageResponse);
			}
			author = authorRepository.save(author);
			if (author != null) {
				authorResponse.setAuthor(author);
				authorResponse.setMessage(Logs.UPDATE_AUTHOR_SUCCESS.getMessage());
				return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.OK);
			} else {
				authorResponse.setMessage(Logs.UPDATE_AUTHOR_UNSUCCESS.getMessage());
				return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			String message = Logs.AUTHOR_NOT_EXIST.getMessage().replace("%ID%", id);
			authorResponse.setMessage(message);
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteAuthor(String id) {
		AuthorResponse authorResponse = new AuthorResponse();
		Author author = authorRepository.findById(id).orElse(null);
		if (author != null) {
			authorRepository.delete(author);
			authorResponse.setAuthor(null);
			authorResponse.setMessage(Logs.DELETE_AUTHOR_SUCCESS.getMessage());
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.OK);
		} else {
			String message = Logs.AUTHOR_NOT_EXIST.getMessage().replace("%ID%", id);
			authorResponse.setMessage(message);
			return new ResponseEntity<AuthorResponse>(authorResponse, HttpStatus.NO_CONTENT);
		}
	}

}
