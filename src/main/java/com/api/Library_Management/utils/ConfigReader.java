package com.api.Library_Management.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config.properties")
public class ConfigReader {
	public static String BOOK_IMAGE_PATH_STR;
	
//	public static final Path BOOK_IMAGE_PATH = Paths.get(BOOK_IMAGE_PATH_STR);
	
	@Value("${book.image.path}")
	private void setBookImagePath(String path) {
		ConfigReader.BOOK_IMAGE_PATH_STR = path;
	}
}
