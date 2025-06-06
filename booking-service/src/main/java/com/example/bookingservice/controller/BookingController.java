package com.example.bookingservice.controller;

import com.example.bookingservice.model.Booking;
import com.example.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> book(@RequestBody Booking booking,
                                        @org.springframework.security.core.annotation.AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject()); // або jwt.getClaim("user_id") якщо claim кастомний
        booking.setUserId(userId);

        return ResponseEntity.ok(bookingService.create(booking));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getUserBookings(@org.springframework.security.core.annotation.AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
