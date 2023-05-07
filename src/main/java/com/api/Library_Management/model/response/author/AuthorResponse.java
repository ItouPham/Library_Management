package com.api.Library_Management.model.response.author;

import com.api.Library_Management.model.response.NotificationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
	private ObjAuthor author;
	private NotificationResponse notification;
}
