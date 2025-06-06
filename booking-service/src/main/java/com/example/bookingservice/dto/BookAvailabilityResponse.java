package com.example.bookingservice.dto;

import lombok.Data;

@Data
public class BookAvailabilityResponse {
    private Long bookId;
    private boolean available;
}
