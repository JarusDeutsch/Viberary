package com.example.bookingservice.controller;

import com.example.bookingservice.client.UserServiceClient;
import com.example.bookingservice.dto.BookingResponse;
import com.example.bookingservice.dto.UserResponse;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserServiceClient userServiceClient;

    @PostMapping
    public ResponseEntity<Booking> book(@RequestBody Booking booking,
                                        @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        UserResponse user = userServiceClient.getUserByEmail(email);

        if (user == null) {
            throw new RuntimeException("❌ Користувач не знайдений у user-service для email: " + email);
        }

        if (user.id() == null) {
            throw new RuntimeException("❌ user.id() == null для email: " + email);
        }

        booking.setUserId(user.id());
        log.info("📦 Бронюємо для користувача: {}", user);

        return ResponseEntity.ok(bookingService.create(booking));
    }


    @GetMapping
    public ResponseEntity<List<Booking>> getUserBookings(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        UserResponse user = userServiceClient.getUserByEmail(email);

        return ResponseEntity.ok(bookingService.getUserBookings(user.id()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getUserBookingsDto(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        UserResponse user = userServiceClient.getUserByEmail(email);

        List<BookingResponse> bookings = bookingService.getUserBookingResponses(user.id());
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<Void> finishBooking(@PathVariable Long id,
                                              @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        UserResponse user = userServiceClient.getUserByEmail(email);

        bookingService.finish(id, user.id());
        return ResponseEntity.noContent().build();
    }
}
