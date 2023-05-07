package com.api.Library_Management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.api.Library_Management.entity.Author;
import com.api.Library_Management.entity.Book;
import com.api.Library_Management.entity.Category;

public interface BookRepository extends MongoRepository<Book, String> {

	List<Book> findByAuthors(Author author);

	List<Book> findByCategories(Category category);

}
