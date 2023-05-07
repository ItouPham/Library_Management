package com.api.Library_Management.model.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

	private String name;

	private String publishedDate;

	private String publishedBy;
	
	private String language;

	private String totalPages;
	
	private MultipartFile image;

	private List<AuthorRequest> authors;

	private List<CategoryRequest> categories;
}