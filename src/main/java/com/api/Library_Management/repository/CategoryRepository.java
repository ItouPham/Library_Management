package com.api.Library_Management.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.api.Library_Management.entity.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

	Optional<Category> findByName(String name);
}
