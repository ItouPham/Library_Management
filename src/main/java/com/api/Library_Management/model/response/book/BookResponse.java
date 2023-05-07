package com.api.Library_Management.model.response.book;

import com.api.Library_Management.model.response.NotificationResponse;

import lombok.Data;

@Data
public class BookResponse {
	private ObjBook book;
	private NotificationResponse notification;
}
