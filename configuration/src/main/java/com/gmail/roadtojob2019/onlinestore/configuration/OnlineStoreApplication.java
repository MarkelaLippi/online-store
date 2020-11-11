package com.gmail.roadtojob2019.onlinestore.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.gmail.roadtojob2019.onlinestore.controller",
		"com.gmail.roadtojob2019.onlinestore.service",
		"com.gmail.roadtojob2019.onlinestore.repository"})
@EntityScan(basePackages = "com.gmail.roadtojob2019.onlinestore.repository.entity")
public class OnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
	}

}
