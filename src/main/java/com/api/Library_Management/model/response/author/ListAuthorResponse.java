package com.api.Library_Management.model.response.author;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAuthorResponse {
	private List<ObjAuthor> authors;
	private String message;
}
