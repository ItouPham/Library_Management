package com.api.Library_Management;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.api.Library_Management.model.response.book.BookImageResponse;
import com.api.Library_Management.model.response.book.ObjBookImage;
import com.api.Library_Management.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@SpringBootApplication
public class LibraryManagementApplication  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		String data = "{\"data\":{\"id\":\"hfunFyI\",\"title\":null,\"description\":null,\"datetime\":1686376355,\"type\":\"image\\/jpeg\",\"animated\":false,\"width\":1000,\"height\":1500,\"size\":178554,\"views\":0,\"bandwidth\":0,\"vote\":null,\"favorite\":false,\"nsfw\":null,\"section\":null,\"account_url\":null,\"account_id\":171455133,\"is_ad\":false,\"in_most_viral\":false,\"has_sound\":false,\"tags\":[],\"ad_type\":0,\"ad_url\":\"\",\"edited\":\"0\",\"in_gallery\":false,\"deletehash\":\"E6dRK52TImiYtga\",\"name\":\"\",\"link\":\"https:\\/\\/i.imgur.com\\/hfunFyI.jpg\"},\"success\":true,\"status\":200}";
//		System.out.println(data);
//		ObjectMapper objectMapper = new ObjectMapper();
//		ObjBookImage bookImage = objectMapper.readValue(data, ObjBookImage.class);
//		System.out.println(bookImage.getData());
//		BookImageResponse bookImageResponse = new BookImageResponse();
//		bookImageResponse.setId((String) bookImage.getData().get("id"));
//		bookImageResponse.setDeletehash((String) bookImage.getData().get("deletehash"));
//		bookImageResponse.setDescription((String) bookImage.getData().get("description"));
//		bookImageResponse.setName((String) bookImage.getData().get("name"));
//		bookImageResponse.setTitle((String) bookImage.getData().get("title"));
//		System.out.println(bookImageResponse);
//		int firstIndex = data.indexOf("{",1) +1;
//		int lastIndex = data.substring(1,data.length()-1).lastIndexOf("}") + 1;
//		String response = data.substring(firstIndex + 1,lastIndex + 1);
//		System.out.println(response);
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args -> {
//			storageService.init();
//		});
//	}

}
