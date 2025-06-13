import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingService, Booking } from '../../services/booking.service';
import { MatButtonModule } from '@angular/material/button';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    NgClass,
    MatButtonModule
  ],
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss']
})
export class UserDashboardComponent implements OnInit {
  bookings: Booking[] = [];

  constructor(private bookingService: BookingService) {}

  ngOnInit(): void {
    this.bookingService.getUserBookings().subscribe((bookings: Booking[]) => {
      const today = new Date();
      this.bookings = bookings.map((booking: Booking) => {
        const end = new Date(booking.endDate);
        if (booking.status !== 'CANCELLED' && end < today) {
          return { ...booking, status: 'FINISHED' };
        }
        return booking;
      });
    });
  }

  markAsFinished(id: number) {
    this.bookingService.markAsFinished(id).subscribe(() => {
      this.bookings = this.bookings.map(b =>
        b.id === id ? { ...b, status: 'FINISHED' } : b
      );
    });
  }
}
