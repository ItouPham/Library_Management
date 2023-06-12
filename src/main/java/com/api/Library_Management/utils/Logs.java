package com.api.Library_Management.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Logs {
	ERROR_SYSTEM("Error System. Please check it again"),
	ADD_AUTHOR_SUCCESS("Add author successfully"),
	ADD_AUTHOR_UNSUCCESS("Add author unsuccessfully"),
	GET_DATA_SUCCESS("Get data successfully"),
	GET_DATA_UNSUCCESS("Get data unsuccessfully"),
	AUTHOR_NOT_EXIST("Author not exist"),
	UPDATE_AUTHOR_SUCCESS("Update author successfully"),
	DELETE_AUTHOR_SUCCESS("Delete author successfully"),
	ADD_CATEGORY_SUCCESS("Add category successfully"),
	CATEGORY_NOT_EXIST("Category not exist"),
	UPDATE_CATEGORY_SUCCESS("Update category successfully"),
	DELETE_CATEGORY_SUCCESS("Delete category successfully"),
	ADD_BOOK_SUCCESS("Add book successfully"),
	ADD_BOOK_UNSUCCESS("Add book unsuccessfully"),
	BOOK_NOT_EXIST("Book not exist"),
	UPDATE_BOOK_SUCCESS("Update book successfully"),
	UPDATE_BOOK_UNSUCCESS("Update book unsuccessfully"),
	DELETE_BOOK_SUCCESS("Delete book successfully"),
	UPLOAD_IMAGE_UNSUCCESS("Upload image unsuccessfully"),
	DELETE_IMAGE_UNSUCCESS("Delete image unsuccessfully");
	
	private String message;
	
}
