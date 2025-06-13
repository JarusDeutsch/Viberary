import { Component, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { BookService, Book } from '../services/book.service';
import { BookingService } from '../services/booking.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  books: Book[] = [];

  constructor(
    private bookService: BookService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.bookService.getAllBooks().subscribe({
      next: (data) => this.books = data,
      error: (err) => console.error('❌ Error loading books', err)
    });
  }

  bookNow(bookId: number): void {
    const today = new Date();
    const end = new Date();
    end.setDate(today.getDate() + 14); // 2 тижні

    const reservation: any = {
      bookId,
      startDate: formatDate(today, 'yyyy-MM-dd', 'en'),
      endDate: formatDate(end, 'yyyy-MM-dd', 'en'),
    };

    this.bookingService.createReservation(reservation).subscribe({
      next: () => alert('✅ Заброньовано успішно'),
      error: (err) => alert('❌ Помилка бронювання: ' + err.message)
    });
  }
}
