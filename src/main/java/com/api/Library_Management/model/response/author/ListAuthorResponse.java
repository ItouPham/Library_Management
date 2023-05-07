package com.api.Library_Management.model.response.author;

import java.util.List;

import com.api.Library_Management.model.response.NotificationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAuthorResponse {
	private List<ObjAuthor> authors;
	private NotificationResponse notification;
}
