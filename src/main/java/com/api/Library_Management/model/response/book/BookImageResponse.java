package com.api.Library_Management.model.response.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookImageResponse {
	private String name;
	private String deletehash;
	private String link;
}
