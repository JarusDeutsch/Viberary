package com.example.bookingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long bookId;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public enum BookingStatus {
        PENDING, CONFIRMED, FINISHED, CANCELLED
    }

}
