package com.api.Library_Management.model.response.category;

import com.api.Library_Management.model.response.NotificationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
	private ObjCategory category;
	private NotificationResponse notification;
}