package com.api.Library_Management.model.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {
	private String id;

	private String name;

	private String gender;
	
	private MultipartFile avatar;
	
	private String about;
}
