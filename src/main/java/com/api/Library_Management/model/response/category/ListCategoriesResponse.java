package com.api.Library_Management.model.response.category;

import java.util.List;

import com.api.Library_Management.model.response.NotificationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCategoriesResponse {
	private List<ObjCategory> categories;
	private NotificationResponse notification;
}
