package com.api.Library_Management.model.response.book;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjBookImage {
	private  Map<String, Object> data; 
	private boolean success;
	private int status;
}
