package com.api.Library_Management.model.response.image;

import java.util.Map;

import lombok.Data;

@Data
public class ObjImage {
	private  Map<String, Object> data; 
	private boolean success;
	private int status;
}
