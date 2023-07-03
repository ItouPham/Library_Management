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
	AUTHOR_NOT_EXIST("Author with Id %ID% is not exist"),
	AUTHOR_HAS_EXISTED("Author has existed"),
	UPDATE_AUTHOR_SUCCESS("Update author successfully"),
	UPDATE_AUTHOR_UNSUCCESS("Update author unsuccessfully"),
	DELETE_AUTHOR_SUCCESS("Delete author successfully"),
	ADD_CATEGORY_SUCCESS("Add category successfully"),
	ADD_CATEGORY_UNSUCCESS("Add category unsuccessfully"),
	CATEGORY_NOT_EXIST("Category with Id %ID% is not exist"),
	CATEGORY_HAS_EXISTED("Category has existed"),
	UPDATE_CATEGORY_SUCCESS("Update category successfully"),
	UPDATE_CATEGORY_UNSUCCESS("Update category unsuccessfully"),
	DELETE_CATEGORY_SUCCESS("Delete category successfully"),
	ADD_BOOK_SUCCESS("Add book successfully"),
	ADD_BOOK_UNSUCCESS("Add book unsuccessfully"),
	BOOK_NOT_EXIST("Book not exist"),
	BOOK_HAS_EXISTED("Book has existed"),
	UPDATE_BOOK_SUCCESS("Update book successfully"),
	UPDATE_BOOK_UNSUCCESS("Update book unsuccessfully"),
	DELETE_BOOK_SUCCESS("Delete book successfully"),
	UPLOAD_IMAGE_UNSUCCESS("Upload image unsuccessfully"),
	DELETE_IMAGE_UNSUCCESS("Delete image unsuccessfully"),
	NULL_CATEGORY_NAME("Category's name can not be empty"),
	NULL_BOOK_NAME("Book's name can not be empty"),
	NULL_AUTHOR_NAME("Author's name can not be empty");
	private String message;
	
}
