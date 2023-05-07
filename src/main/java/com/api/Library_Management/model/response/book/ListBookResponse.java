package com.api.Library_Management.model.response.book;

import java.util.List;

import com.api.Library_Management.model.response.NotificationResponse;

import lombok.Data;

@Data
public class ListBookResponse {
	private List<ObjBook> books;
	private NotificationResponse notification;

}
