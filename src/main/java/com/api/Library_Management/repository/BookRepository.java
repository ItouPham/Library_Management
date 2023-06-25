package com.api.Library_Management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.api.Library_Management.entity.Author;
import com.api.Library_Management.entity.Book;
import com.api.Library_Management.entity.Category;
import com.api.Library_Management.model.bean.ObjBeanAuthor;

public interface BookRepository extends MongoRepository<Book, String> {

	Optional<Book> findByName(String name);
	
	List<Book> findByAuthorId(String id);
	
	List<Book> findByCategories(Category category);

	Page<Book> findByCategoriesOrderByCreatedDateDesc(Category category, Pageable pageable);
	
	Page<Book> findByOrderByCreatedDateDesc(Pageable pageable);

}
