package com.api.Library_Management.model.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BookRequest {

	private String name;

	private String publishedDate;

	private String publishedBy;
	
	private String language;

	private String pageNumbers;
	
	private MultipartFile image;
	
	private String bookCover;
	
	private String shortDescription;
	
	private String authorId;

	private List<String> categoryIds = new ArrayList<>();
}
