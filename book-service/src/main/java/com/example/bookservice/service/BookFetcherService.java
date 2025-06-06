package com.example.bookservice.service;

import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookFetcherService {
    private final BookRepository bookRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public void fetchAndSaveBooks() {
        if (bookRepository.count() > 0) {
            System.out.println("База книг уже заполнена");
            return;
        }

        String url = "https://www.googleapis.com/books/v1/volumes?q=subject:fiction&maxResults=5";

        var response = restTemplate.getForObject(url, GoogleBooksResponse.class);

        if (response != null && response.items != null) {
            response.items.forEach(item -> {
                var volumeInfo = item.volumeInfo;
                if (volumeInfo.title != null && volumeInfo.authors != null) {
                    bookRepository.save(Book.builder()
                            .title(volumeInfo.title)
                            .author(String.join(", ", volumeInfo.authors))
                            .genre(volumeInfo.categories != null ? String.join(", ", volumeInfo.categories) : "Unknown")
                            .description(volumeInfo.description)
                            .demoUrl(volumeInfo.previewLink)
                            .totalCopies(5)
                            .availableCopies(5)
                            .available(true)
                            .createdAt(LocalDateTime.now())
                            .build());
                }
            });

            System.out.println("Книги успешно загружены из API.");
        } else {
            System.out.println("Ошибка при получении данных из API.");
        }
    }

}
