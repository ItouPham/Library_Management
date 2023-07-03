package com.api.Library_Management.service.impl;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.Library_Management.entity.Author;
import com.api.Library_Management.entity.Book;
import com.api.Library_Management.entity.Category;
import com.api.Library_Management.model.bean.ObjBeanAuthor;
import com.api.Library_Management.model.bean.ObjBeanImage;
import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.BookResponse;
import com.api.Library_Management.model.response.book.ListBookResponse;
import com.api.Library_Management.model.response.book.ObjBook;
import com.api.Library_Management.model.response.image.ObjImage;
import com.api.Library_Management.repository.AuthorRepository;
import com.api.Library_Management.repository.BookRepository;
import com.api.Library_Management.repository.CategoryRepository;
import com.api.Library_Management.service.BookService;
import com.api.Library_Management.service.StorageService;
import com.api.Library_Management.utils.DateUtil;
import com.api.Library_Management.utils.Logs;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private StorageService storageService;

	@Override
	public ListBookResponse getAllBooks(int page, int size) {
		ListBookResponse listBooksResponse = new ListBookResponse();
		if (page != 0) {
			page--;
		}
		Pageable paging = PageRequest.of(page, size);
		Page<Book> books = bookRepository.findByOrderByCreatedDateDesc(paging);
		List<Category> categories = categoryRepository.findAll();
		listBooksResponse.setCategories(categories);
		listBooksResponse.setCurrentPage(books.getNumber() + 1);
		listBooksResponse.setTotalItems(books.getTotalElements());
		listBooksResponse.setTotalPages(books.getTotalPages());
		listBooksResponse.setBooks(books.toList());
		listBooksResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
		return listBooksResponse;
	}

	@Override
	public ResponseEntity<?> getBookById(String id) {
		BookResponse bookResponse = new BookResponse();
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			bookResponse.setBook(book);
			bookResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			return new ResponseEntity<>(bookResponse, HttpStatus.OK);
		} else {
			bookResponse.setMessage(Logs.BOOK_NOT_EXIST.getMessage());
			return new ResponseEntity<>(bookResponse, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> createNewBook(BookRequest bookRequest) throws IOException {
		BookResponse bookResponse = new BookResponse();
		Book savedBook = new Book();
		Category objCategory = new Category();
		Author objAuthor = new Author();
		List<Category> listCategories = new ArrayList<>();
		ObjBeanAuthor objBeanAuthor = new ObjBeanAuthor();
		Book book = bookRepository.findByName(bookRequest.getName()).orElse(null);
		if (book != null) {
			bookResponse.setMessage(Logs.BOOK_HAS_EXISTED.getMessage());
			return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.CONFLICT);
		} else {
			BeanUtils.copyProperties(bookRequest, savedBook);
			for (String categoryId : bookRequest.getCategoryIds()) {
				objCategory = categoryRepository.findById(categoryId).orElse(null);
				if (objCategory != null) {
					listCategories.add(objCategory);
				} else {
					String message = Logs.CATEGORY_NOT_EXIST.getMessage().replace("%ID%", categoryId);
					bookResponse.setMessage(message);
					return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.NOT_FOUND);
				}
			}

			objAuthor = authorRepository.findById(bookRequest.getAuthorId()).orElse(null);
			if (objAuthor != null) {
				objBeanAuthor.setId(objAuthor.getId());
				objBeanAuthor.setName(objAuthor.getName());
			} else {
				String message = Logs.AUTHOR_NOT_EXIST.getMessage().replace("%ID%", bookRequest.getAuthorId());
				bookResponse.setMessage(message);
				return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.NOT_FOUND);
			}

			if (!bookRequest.getImage().isEmpty()) {
				ObjImage objBookImage = storageService.postImageToImgur(bookRequest.getImage(), bookRequest.getName(),
						"BOOK");
				if (objBookImage.getStatus() != 200) {
					bookResponse.setMessage(Logs.UPLOAD_IMAGE_UNSUCCESS.getMessage());
					return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				ObjBeanImage bookImageResponse = responseToEntity(objBookImage.getData());
				savedBook.setImage(bookImageResponse);
			}
			savedBook.setCreatedDate(DateUtil.formatDate(ZonedDateTime.now()));
			savedBook.setCategories(listCategories);
			savedBook.setAuthor(objBeanAuthor);
			savedBook = bookRepository.save(savedBook);
			if (savedBook != null) {
				bookResponse.setBook(savedBook);
				bookResponse.setMessage(Logs.ADD_BOOK_SUCCESS.getMessage());
				return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.CREATED);
			} else {
				bookResponse.setMessage(Logs.ADD_BOOK_UNSUCCESS.getMessage());
				return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}

	@Override
	public ResponseEntity<?> editBook(String id, BookRequest bookRequest) throws IOException {
		BookResponse bookResponse = new BookResponse();
		Category objCategory = new Category();
		Author objAuthor = new Author();
		List<Category> listCategories = new ArrayList<>();
		ObjBeanAuthor objBeanAuthor = new ObjBeanAuthor();
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			BeanUtils.copyProperties(bookRequest, book);
			for (String categoryId : bookRequest.getCategoryIds()) {
				objCategory = categoryRepository.findById(categoryId).orElse(null);
				if (objCategory != null) {
					listCategories.add(objCategory);
				} else {
					String message = Logs.CATEGORY_NOT_EXIST.getMessage().replace("%ID%", categoryId);
					bookResponse.setMessage(message);
					return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.NO_CONTENT);
				}
			}

			objAuthor = authorRepository.findById(bookRequest.getAuthorId()).orElse(null);
			if (objAuthor != null) {
				objBeanAuthor.setId(objAuthor.getId());
				objBeanAuthor.setName(objAuthor.getName());
			} else {
				String message = Logs.AUTHOR_NOT_EXIST.getMessage().replace("%ID%", bookRequest.getAuthorId());
				bookResponse.setMessage(message);
				return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.NO_CONTENT);
			}

			if (!bookRequest.getImage().isEmpty()) {
				ResponseEntity<String> deleteImageResponse = storageService
						.deleteImageFromImgur(book.getImage().getDeletehash());
				if (deleteImageResponse.getStatusCodeValue() != 200) {
					bookResponse.setMessage(Logs.DELETE_IMAGE_UNSUCCESS.getMessage());
					return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.resolve(deleteImageResponse.getStatusCodeValue()));
				}
				ObjImage objBookImage = storageService.postImageToImgur(bookRequest.getImage(), bookRequest.getName(),
						"BOOK");
				if (objBookImage.getStatus() != 200) {
					bookResponse.setMessage(Logs.UPLOAD_IMAGE_UNSUCCESS.getMessage());
					return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.resolve(objBookImage.getStatus()));
				}
				ObjBeanImage bookImageResponse = responseToEntity(objBookImage.getData());
				book.setImage(bookImageResponse);
			}

			book.setCategories(listCategories);
			book.setAuthor(objBeanAuthor);
			book = bookRepository.save(book);
			if (book != null) {
				bookResponse.setBook(book);
				bookResponse.setMessage(Logs.UPDATE_BOOK_SUCCESS.getMessage());
				return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.OK);
			} else {
				bookResponse.setMessage(Logs.UPDATE_BOOK_UNSUCCESS.getMessage());
				return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			bookResponse.setMessage(Logs.BOOK_NOT_EXIST.getMessage());
			return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.NO_CONTENT);

		}
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteBook(String id) {
		BookResponse bookResponse = new BookResponse();
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			ResponseEntity<String> deleteImageResponse = storageService
					.deleteImageFromImgur(book.getImage().getDeletehash());
			if (deleteImageResponse.getStatusCodeValue() != 200) {
				bookResponse.setMessage(Logs.DELETE_IMAGE_UNSUCCESS.getMessage());
				return new ResponseEntity<BookResponse>(bookResponse,
						HttpStatus.resolve(deleteImageResponse.getStatusCodeValue()));
			}
			bookRepository.delete(book);
			bookResponse.setBook(null);
			bookResponse.setMessage(Logs.DELETE_BOOK_SUCCESS.getMessage());
			return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.OK);
		} else {
			bookResponse.setMessage(Logs.BOOK_NOT_EXIST.getMessage());
			return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.NO_CONTENT);
		}

	}

	@Override
	public ResponseEntity<?> getBooksByAuthorId(String id) {
		ListBookResponse listBooksResponse = new ListBookResponse();
		Author author = authorRepository.findById(id).orElse(null);
		if (author != null) {
			List<Book> books = bookRepository.findByAuthorId(id);
			listBooksResponse.setBooks(books);
			listBooksResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			return new ResponseEntity<ListBookResponse>(listBooksResponse, HttpStatus.OK);
		} else {
			String message = Logs.AUTHOR_NOT_EXIST.getMessage().replace("%ID%", id);
			listBooksResponse.setMessage(message);
			return new ResponseEntity<ListBookResponse>(listBooksResponse, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<?> getBooksByCategoryId(String id, int page, int size) {
		ListBookResponse listBooksResponse = new ListBookResponse();
		Category category = categoryRepository.findById(id).orElse(null);
		if (category != null) {
			if (page != 0) {
				page--;
			}
			Pageable paging = PageRequest.of(page, size);
			Page<Book> books = bookRepository.findByCategoriesOrderByCreatedDateDesc(category, paging);
			List<Category> categories = categoryRepository.findAll();
			listBooksResponse.setCategories(categories);
			listBooksResponse.setCurrentPage(books.getNumber() + 1);
			listBooksResponse.setTotalItems(books.getTotalElements());
			listBooksResponse.setTotalPages(books.getTotalPages());
			listBooksResponse.setBooks(books.toList());
			listBooksResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			return new ResponseEntity<ListBookResponse>(listBooksResponse, HttpStatus.OK);
		} else {
			listBooksResponse.setMessage(Logs.CATEGORY_NOT_EXIST.getMessage());
			return new ResponseEntity<ListBookResponse>(listBooksResponse, HttpStatus.NO_CONTENT);
		}
	}

	private ObjBeanImage responseToEntity(Map<String, Object> response) {
		ObjBeanImage bookImageResponse = new ObjBeanImage();
		bookImageResponse.setDeletehash((String) response.get("deletehash"));
		bookImageResponse.setName((String) response.get("name"));
		bookImageResponse.setLink((String) response.get("link"));
		return bookImageResponse;
	}

}
