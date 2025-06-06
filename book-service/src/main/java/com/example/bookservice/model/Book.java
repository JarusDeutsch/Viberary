package com.example.bookservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;

    @Column(length = 100000)
    private String description;

    private String demoUrl;

    private int totalCopies;
    private int availableCopies;

    private boolean available;
    private LocalDateTime createdAt;
}
