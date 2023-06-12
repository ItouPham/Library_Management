package com.api.Library_Management.model.response.book;

import lombok.Data;

@Data
public class BookResponse {
	private ObjBook book;
	private String message;
}
