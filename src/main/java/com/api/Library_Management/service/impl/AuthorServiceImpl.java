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
				authorResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			authorResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
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
				authorResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			} else {
				authorResponse.setMessage(Logs.AUTHOR_NOT_EXIST.getMessage());
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			authorResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return authorResponse;
		}
	}

	@Override
	@Transactional
	public AuthorResponse createNewAuthor(AuthorRequest authorRequest) {
		AuthorResponse authorResponse = new AuthorResponse();
		ObjAuthor objAuthor = new ObjAuthor();
		Author savedAuthor = new Author();
		try {
			Author author = authorRepository.findByName(authorRequest.getName()).orElse(null);
			if(author != null) {
				authorResponse.setMessage(Logs.AUTHOR_HAS_EXISTED.getMessage());
				return authorResponse;
			} else {				
				BeanUtils.copyProperties(authorRequest, savedAuthor);
				savedAuthor = authorRepository.save(savedAuthor);
				if(savedAuthor != null) {
					BeanUtils.copyProperties(savedAuthor, objAuthor);
					authorResponse.setAuthor(objAuthor);
					authorResponse.setMessage(Logs.ADD_AUTHOR_SUCCESS.getMessage());					
				} else {
					authorResponse.setMessage(Logs.ADD_AUTHOR_UNSUCCESS.getMessage());
				}
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			authorResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
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
				authorResponse.setMessage(Logs.UPDATE_AUTHOR_SUCCESS.getMessage());
			} else {
				authorResponse.setMessage(Logs.AUTHOR_NOT_EXIST.getMessage());
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			authorResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
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
				authorResponse.setMessage(Logs.DELETE_AUTHOR_SUCCESS.getMessage());
			} else {
				authorResponse.setMessage(Logs.AUTHOR_NOT_EXIST.getMessage());
			}
			return authorResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			authorResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return authorResponse;
		}
	}

}
