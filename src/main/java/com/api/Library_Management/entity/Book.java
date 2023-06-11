package com.api.Library_Management.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.api.Library_Management.model.response.book.BookImageResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

	@Id
	private String id;
	
	private String name;
	
	private String publishedDate;
	
	private String publishedBy;
	
	private String language; 
	
	private String totalPages;
	
	private BookImageResponse image;
	
	private List<Author> authors;
	
	private List<Category> categories;
	
}
