package com.gmail.roadtojob2019.onlinestore.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gmail.roadtojob2019.onlinestore.controller")
public class OnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
	}

}
