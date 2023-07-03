package com.api.Library_Management.model.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AuthorRequest {
	private String name;

	private String gender;
	
	private MultipartFile avatar;
	
	private String about;
}
