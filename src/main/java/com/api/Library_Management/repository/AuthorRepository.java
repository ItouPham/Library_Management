package com.api.Library_Management.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.api.Library_Management.entity.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
	Optional<Author> findByName(String name);
}
