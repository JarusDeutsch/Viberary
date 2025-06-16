package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.exception.BookUnavailableException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookServiceClient bookServiceClient;

    public Booking create(Booking booking) {
        boolean conflict = bookingRepository.hasConflict(
                booking.getBookId(),
                booking.getStartDate(),
                booking.getEndDate()
        );

        if (conflict) {
            throw new BookUnavailableException("Book is already reserved for the selected period");
        }

        booking.setStatus(Booking.BookingStatus.PENDING);
        return bookingRepository.save(booking);
    }

    public List<BookingResponse> getUserBookingResponses(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(booking -> BookingResponse.builder()
                        .id(booking.getId())
                        .bookId(booking.getBookId())
                        .startDate(booking.getStartDate())
                        .endDate(booking.getEndDate())
                        .status(booking.getStatus().name())
                        .build())
                .toList();
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public void finish(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUserId().equals(userId)) {
            throw new RuntimeException("Access denied: this booking does not belong to you");
        }

        if (booking.getStatus() != Booking.BookingStatus.CANCELLED) {
            booking.setStatus(Booking.BookingStatus.FINISHED); // або CONFIRMED → FINISHED
            bookingRepository.save(booking);
        }
    }

}
