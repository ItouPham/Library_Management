package com.api.Library_Management.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.api.Library_Management.entity.Author;
import com.api.Library_Management.entity.Book;
import com.api.Library_Management.model.request.AuthorRequest;
import com.api.Library_Management.model.response.NotificationResponse;
import com.api.Library_Management.model.response.author.AuthorResponse;
import com.api.Library_Management.model.response.author.ListAuthorResponse;
import com.api.Library_Management.model.response.author.ObjAuthor;
import com.api.Library_Management.repository.AuthorRepository;
import com.api.Library_Management.repository.BookRepository;
import com.api.Library_Management.service.AuthorService;
import com.api.Library_Management.utils.Logs;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Override
	public ListAuthorResponse getAllAuthors() {
		ListAuthorResponse authorResponse = new ListAuthorResponse();
		List<ObjAuthor> listAuthorResponse = new ArrayList<>();
		List<Author> listAuthors = new ArrayList<>();
		try {
			listAuthors = authorRepository.findAll();
			if (listAuthors != null && listAuthors.size() > 0) {
				for (Author author : listAuthors) {
					ObjAuthor objAuthor = new ObjAuthor();
					BeanUtils.copyProperties(author, objAuthor);
					listAuthorResponse.add(objAuthor);
				}
				authorResponse.setAuthors(listAuthorResponse);
				authorResponse.setNotification(new NotificationResponse(Logs.GET_DATA_SUCCESS.getMessage()));
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			authorResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return authorResponse;
		}
	}

	@Override
	public AuthorResponse getAuthorById(String id) {
		AuthorResponse authorResponse = new AuthorResponse();
		ObjAuthor objAuthor = new ObjAuthor();
		Author author = new Author();
		try {
			author = authorRepository.findById(id).orElse(null);
			if (author != null) {
				BeanUtils.copyProperties(author, objAuthor);
				authorResponse.setAuthor(objAuthor);
				authorResponse.setNotification(new NotificationResponse(Logs.GET_DATA_SUCCESS.getMessage()));
			} else {
				authorResponse.setNotification(new NotificationResponse(Logs.AUTHOR_NOT_EXIST.getMessage()));
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			authorResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return authorResponse;
		}
	}

	@Override
	@Transactional
	public AuthorResponse createNewAuthor(AuthorRequest authorRequest) {
		AuthorResponse authorResponse = new AuthorResponse();
		ObjAuthor objAuthor = new ObjAuthor();
		try {
			Author author = new Author();
			BeanUtils.copyProperties(authorRequest, author);
			authorRepository.save(author);
			BeanUtils.copyProperties(author, objAuthor);
			authorResponse.setAuthor(objAuthor);
			authorResponse.setNotification(new NotificationResponse(Logs.ADD_AUTHOR_SUCCESS.getMessage()));
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			authorResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return authorResponse;
		}
	}

	@Override
	@Transactional
	public AuthorResponse editAuthor(String id, AuthorRequest authorRequest) {
		AuthorResponse authorResponse = new AuthorResponse();
		ObjAuthor objAuthor = new ObjAuthor();
		Author author = new Author();
		try {
			author = authorRepository.findById(id).orElse(null);
			if (author != null) {
				BeanUtils.copyProperties(authorRequest, author);
				BeanUtils.copyProperties(author, objAuthor);
				authorRepository.save(author);
				authorResponse.setAuthor(objAuthor);
				authorResponse.setNotification(new NotificationResponse(Logs.UPDATE_AUTHOR_SUCCESS.getMessage()));
			} else {
				authorResponse.setNotification(new NotificationResponse(Logs.AUTHOR_NOT_EXIST.getMessage()));
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			authorResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return authorResponse;
		}
	}

	@Override
	@Transactional
	public AuthorResponse deleteAuthor(String id) {
		AuthorResponse authorResponse = new AuthorResponse();
		ObjAuthor objAuthor = new ObjAuthor();
		Author author = new Author();
		try {
			author = authorRepository.findById(id).orElse(null);
			if (author != null) {
				List<Book> books = bookRepository.findByAuthors(author);
				if (books != null) {
					for (Book book : books) {
						book.getAuthors().removeIf(a -> a.getId().equals(id));
					}
					bookRepository.saveAll(books);
				}
				BeanUtils.copyProperties(author, objAuthor);
				authorRepository.delete(author);
				authorResponse.setAuthor(objAuthor);
				authorResponse.setNotification(new NotificationResponse(Logs.DELETE_AUTHOR_SUCCESS.getMessage()));
			} else {
				authorResponse.setNotification(new NotificationResponse(Logs.AUTHOR_NOT_EXIST.getMessage()));
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			authorResponse.setNotification(new NotificationResponse(Logs.ERROR_SYSTEM.getMessage()));
			return authorResponse;
		}
	}

}
