package com.api.Library_Management.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.api.Library_Management.model.bean.ObjBeanImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "authors")
public class Author {
	
	@Id
	private String id;
	
	private String name;
	
	private String gender;
	
	private String about;
	
	private ObjBeanImage avatar;
}
