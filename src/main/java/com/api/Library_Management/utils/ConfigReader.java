package com.api.Library_Management.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config.properties")
public class ConfigReader {
	public static String BOOK_IMAGE_PATH_STR;
	public static String AUTHORIZATION_TOKEN;
	public static String POST_BOOK_IMAGE_URL;
	public static String GET_BOOK_IMAGE_URL;

//	public static final Path BOOK_IMAGE_PATH = Paths.get(BOOK_IMAGE_PATH_STR);

	@Value("${book.image.path}")
	private void setBookImagePath(String path) {
		ConfigReader.BOOK_IMAGE_PATH_STR = path;
	}

	@Value("${authorization.token}")
	private void setAuthorizationToken(String token) {
		ConfigReader.AUTHORIZATION_TOKEN = token;
	}

	@Value("${post.book.image.url}")
	private void setPostBookImageURL(String url) {
		ConfigReader.POST_BOOK_IMAGE_URL = url;
	}

	@Value("${get.book.image.url}")
	private void setGetBookImageURL(String url) {
		ConfigReader.GET_BOOK_IMAGE_URL = url;
	}
}
