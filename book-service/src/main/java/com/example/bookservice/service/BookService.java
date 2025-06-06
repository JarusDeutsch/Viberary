package com.example.bookservice.service;

import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Book save(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setAvailableCopies(book.getTotalCopies());
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        book.setId(id);
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
