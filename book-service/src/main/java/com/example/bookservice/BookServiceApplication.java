package com.example.bookservice;

import com.example.bookservice.service.BookFetcherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner fetchBooks(BookFetcherService fetcherService) {
		return args -> fetcherService.fetchAndSaveBooks();
	}

}
