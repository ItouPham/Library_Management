package com.api.Library_Management.model.response.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
	private ObjAuthor author;
	private String message;
}
