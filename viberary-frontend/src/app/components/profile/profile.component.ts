import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingService } from '../../services/booking.service';
import { Booking } from '../../models/booking.model';
import { NgIf, NgFor } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true, // ✅ ключове
  imports: [CommonModule, NgIf, NgFor, RouterModule], // ✅ потрібні модулі
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  bookings: Booking[] = [];

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    this.bookingService.getMyBookings().subscribe({
      next: (bookings: Booking[]) => this.bookings = bookings,
      error: (err: any) => console.error('❌ Не вдалося отримати бронювання:', err)
    });
  }

  markAsRead(id: number): void {
    this.bookingService.finishBooking(id).subscribe({
      next: () => this.loadBookings(),
      error: (err: any) => alert('❌ Помилка завершення бронювання')
    });
  }
}
