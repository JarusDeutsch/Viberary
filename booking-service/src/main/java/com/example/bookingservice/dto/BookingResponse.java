package com.example.bookingservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long bookId;
//    private String bookTitle; // опційно
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
