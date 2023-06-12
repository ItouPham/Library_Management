package com.api.Library_Management.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.api.Library_Management.entity.Author;
import com.api.Library_Management.entity.Book;
import com.api.Library_Management.entity.Category;
import com.api.Library_Management.model.request.BookRequest;
import com.api.Library_Management.model.response.book.BookImageResponse;
import com.api.Library_Management.model.response.book.BookResponse;
import com.api.Library_Management.model.response.book.ListBookResponse;
import com.api.Library_Management.model.response.book.ObjBook;
import com.api.Library_Management.model.response.book.ObjBookImage;
import com.api.Library_Management.repository.AuthorRepository;
import com.api.Library_Management.repository.BookRepository;
import com.api.Library_Management.repository.CategoryRepository;
import com.api.Library_Management.service.BookService;
import com.api.Library_Management.service.StorageService;
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
	public ListBookResponse getAllBooks() {
		List<ObjBook> listObjBooks = new ArrayList<>();
		ListBookResponse listBooksResponse = new ListBookResponse();
		try {
			List<Book> books = bookRepository.findAll();
			for (Book book : books) {
				ObjBook objBook = new ObjBook();
				BeanUtils.copyProperties(book, objBook);
				listObjBooks.add(objBook);
			}
			listBooksResponse.setBooks(listObjBooks);
			listBooksResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			return listBooksResponse;
		} catch (Exception e) {
			e.printStackTrace();
			listBooksResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return listBooksResponse;
		}
	}

	@Override
	public BookResponse getBookById(String id) {
		BookResponse bookResponse = new BookResponse();
		ObjBook objBook = new ObjBook();
		Book book = new Book();
		try {
			book = bookRepository.findById(id).orElse(null);
			if (book != null) {
				BeanUtils.copyProperties(book, objBook);
				bookResponse.setBook(objBook);
				bookResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			} else {
				bookResponse.setMessage(Logs.BOOK_NOT_EXIST.getMessage());
			}
			return bookResponse;
		} catch (Exception e) {
			e.printStackTrace();
			bookResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return bookResponse;
		}
	}

	@Override
	@Transactional
	public BookResponse createNewBook(BookRequest bookRequest) {
		BookResponse bookResponse = new BookResponse();
		ObjBook objBook = new ObjBook();
		Book book = new Book();
		Category objCategory = new Category();
		Author objAuthor = new Author();
		List<Category> listCategories = new ArrayList<>();
		List<Author> listAuthors = new ArrayList<>();
		try {
			BeanUtils.copyProperties(bookRequest, book);
			for (String categoryId : bookRequest.getCategoryIds()) {
				objCategory = categoryRepository.findById(categoryId).orElse(null);
				if (objCategory != null) {
					listCategories.add(objCategory);
				} else {
					bookResponse.setMessage(Logs.CATEGORY_NOT_EXIST.getMessage());
					return bookResponse;
				}
			}

			for (String authorId : bookRequest.getAuthorIds()) {
				objAuthor = authorRepository.findById(authorId).orElse(null);
				if (objAuthor != null) {
					listAuthors.add(objAuthor);
				} else {
					bookResponse.setMessage(Logs.AUTHOR_NOT_EXIST.getMessage());
					return bookResponse;
				}
			}

			if (!bookRequest.getImage().isEmpty()) {
				ObjBookImage objBookImage = storageService.postImageToImgur(bookRequest.getImage(), bookRequest);
				if (objBookImage.getStatus() != 200) {
					bookResponse.setMessage(Logs.UPLOAD_IMAGE_UNSUCCESS.getMessage());
					return bookResponse;
				}
				BookImageResponse bookImageResponse = responseToEntity(objBookImage.getData());
				book.setImage(bookImageResponse);
			}

			book.setCategories(listCategories);
			book.setAuthors(listAuthors);
			book = bookRepository.save(book);
			if (book != null) {
				BeanUtils.copyProperties(book, objBook);
				bookResponse.setMessage(Logs.ADD_BOOK_SUCCESS.getMessage());
			} else {
				bookResponse.setMessage(Logs.ADD_BOOK_UNSUCCESS.getMessage());
			}

			bookResponse.setBook(objBook);
			return bookResponse;
		} catch (Exception e) {
			e.printStackTrace();
			bookResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return bookResponse;
		}
	}

	@Override
	public BookResponse editBook(String id, BookRequest bookRequest) {
		BookResponse bookResponse = new BookResponse();
		ObjBook objBook = new ObjBook();
		Category objCategory = new Category();
		Author objAuthor = new Author();
		List<Category> listCategories = new ArrayList<>();
		List<Author> listAuthors = new ArrayList<>();
		try {
			Book book = bookRepository.findById(id).orElse(null);
			if (book != null) {
				BeanUtils.copyProperties(bookRequest, book);

				for (String categoryId : bookRequest.getCategoryIds()) {
					objCategory = categoryRepository.findById(categoryId).orElse(null);
					if (objCategory != null) {
						listCategories.add(objCategory);
					} else {
						bookResponse.setMessage(Logs.CATEGORY_NOT_EXIST.getMessage());
						return bookResponse;
					}
				}

				for (String authorId : bookRequest.getAuthorIds()) {
					objAuthor = authorRepository.findById(authorId).orElse(null);
					if (objAuthor != null) {
						listAuthors.add(objAuthor);
					} else {
						bookResponse.setMessage(Logs.AUTHOR_NOT_EXIST.getMessage());
						return bookResponse;
					}
				}

				if (!bookRequest.getImage().isEmpty()) {
					ResponseEntity<String> deleteImageResponse = storageService
							.deleteImageFromImgur(book.getImage().getDeletehash());
					if (deleteImageResponse.getStatusCodeValue() != 200) {
						bookResponse.setMessage(Logs.DELETE_IMAGE_UNSUCCESS.getMessage());
						return bookResponse;
					}
					ObjBookImage objBookImage = storageService.postImageToImgur(bookRequest.getImage(), bookRequest);
					if (objBookImage.getStatus() != 200) {
						bookResponse.setMessage(Logs.UPLOAD_IMAGE_UNSUCCESS.getMessage());
						return bookResponse;
					}
					BookImageResponse bookImageResponse = responseToEntity(objBookImage.getData());
					book.setImage(bookImageResponse);
				}

				book.setCategories(listCategories);
				book.setAuthors(listAuthors);
				book = bookRepository.save(book);
				if (book != null) {
					BeanUtils.copyProperties(book, objBook);
					bookResponse.setMessage(Logs.UPDATE_BOOK_SUCCESS.getMessage());
				} else {
					bookResponse.setMessage(Logs.UPDATE_BOOK_UNSUCCESS.getMessage());
				}
			} else {
				bookResponse.setMessage(Logs.BOOK_NOT_EXIST.getMessage());
			}

			bookResponse.setBook(objBook);
			return bookResponse;
		} catch (Exception e) {
			e.printStackTrace();
			bookResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return bookResponse;
		}

	}

	@Override
	@Transactional
	public BookResponse deleteBook(String id) {
		BookResponse bookResponse = new BookResponse();
		Book book = new Book();
		try {
			book = bookRepository.findById(id).orElse(null);
			if (book != null) {
				ResponseEntity<String> deleteImageResponse = storageService
						.deleteImageFromImgur(book.getImage().getDeletehash());
				if (deleteImageResponse.getStatusCodeValue() != 200) {
					bookResponse.setMessage(Logs.DELETE_IMAGE_UNSUCCESS.getMessage());
					return bookResponse;
				}
				bookRepository.delete(book);
				bookResponse.setMessage(Logs.DELETE_BOOK_SUCCESS.getMessage());
			} else {
				bookResponse.setMessage(Logs.BOOK_NOT_EXIST.getMessage());
			}

			bookResponse.setBook(null);
			return bookResponse;

		} catch (Exception e) {
			e.printStackTrace();
			bookResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return bookResponse;
		}
	}

	@Override
	public ListBookResponse getBooksByAuthorId(String id) {
		ListBookResponse listBooksResponse = new ListBookResponse();
		BookResponse bookResponse = new BookResponse();
		List<ObjBook> objBooks = new ArrayList<>();
		try {
			Author author = authorRepository.findById(id).orElse(null);
			if (author != null) {
				List<Book> books = bookRepository.findByAuthors(author);
				for (Book book : books) {
					ObjBook objBook = new ObjBook();
					BeanUtils.copyProperties(book, objBook);
					objBooks.add(objBook);
				}
				listBooksResponse.setBooks(objBooks);
				listBooksResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			} else {
				listBooksResponse.setMessage(Logs.AUTHOR_NOT_EXIST.getMessage());
			}
			return listBooksResponse;

		} catch (Exception e) {
			e.printStackTrace();
			bookResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return listBooksResponse;
		}
	}

	@Override
	public ListBookResponse getBooksByCategoryId(String id) {
		ListBookResponse listBooksResponse = new ListBookResponse();
		BookResponse bookResponse = new BookResponse();
		List<ObjBook> objBooks = new ArrayList<>();
		try {
			Category category = categoryRepository.findById(id).orElse(null);
			if (category != null) {
				List<Book> books = bookRepository.findByCategories(category);
				for (Book book : books) {
					ObjBook objBook = new ObjBook();
					BeanUtils.copyProperties(book, objBook);
					objBooks.add(objBook);
				}
				listBooksResponse.setBooks(objBooks);
				listBooksResponse.setMessage(Logs.GET_DATA_SUCCESS.getMessage());
			} else {
				listBooksResponse.setMessage(Logs.AUTHOR_NOT_EXIST.getMessage());
			}
			return listBooksResponse;
		} catch (Exception e) {
			e.printStackTrace();
			bookResponse.setMessage(Logs.ERROR_SYSTEM.getMessage());
			return listBooksResponse;
		}
	}

	private BookImageResponse responseToEntity(Map<String, Object> response) {
		BookImageResponse bookImageResponse = new BookImageResponse();
		bookImageResponse.setDeletehash((String) response.get("deletehash"));
		bookImageResponse.setName((String) response.get("name"));
		bookImageResponse.setLink((String) response.get("link"));
		return bookImageResponse;
	}

}
