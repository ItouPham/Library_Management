package com.api.Library_Management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.api.Library_Management.entity.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
