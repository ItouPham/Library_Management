package com.api.Library_Management.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.api.Library_Management.model.bean.ObjBeanBookImage;
import com.api.Library_Management.model.bean.ObjBeanAuthor;

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
	
	private String pageNumbers;
	
	private String createdDate;
	
	private ObjBeanBookImage image;
	
	private String bookCover;
	
	private String shortDescription;
	
	private ObjBeanAuthor author;
	
	private List<Category> categories;
	
}
