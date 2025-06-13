package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        List<Book> books = bookService.findAll();
        System.out.println("📘 [GET /books] Кількість знайдених книг: " + books.size());
        for (Book book : books) {
            System.out.println("📗 - " + book.getTitle() + " | " + book.getAuthor());
        }
        return books;
    }

    @GetMapping("/ping")
    public String ping() {
        return "book-service працює";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable Long id) {
        System.out.println("🔍 [GET /books/" + id + "] Запит книги за ID");
        Book book = bookService.findById(id);
        System.out.println("📙 Знайдено: " + book.getTitle() + " | " + book.getAuthor());
        return ResponseEntity.ok(book);
    }

    @PostMapping("/admin")
    public Book create(@RequestBody Book book) {
        System.out.println("🆕 [POST /books/admin] Створення нової книги: " + book.getTitle());
        return bookService.save(book);
    }

    @PutMapping("/admin/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        System.out.println("✏️ [PUT /books/admin/" + id + "] Оновлення книги: " + book.getTitle());
        return bookService.update(id, book);
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        System.out.println("🗑️ [DELETE /books/admin/" + id + "] Видалення книги");
        bookService.delete(id);
    }
}
