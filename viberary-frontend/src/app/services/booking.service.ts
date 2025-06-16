import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking } from '../models/booking.model';

@Injectable({ providedIn: 'root' })
export class BookingService {
  private apiUrl = '/api/bookings';

  constructor(private http: HttpClient) {}

  /** Отримати поточні бронювання користувача */
  getMyBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.apiUrl}/my`);
  }

  /** Завершити бронювання вручну */
  finishBooking(id: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/finish`, {});
  }

  /** Створити нову резервацію (без вставки токена вручну) */
  createReservation(reservation: {
    bookId: number;
    startDate: string;
    endDate: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}`, reservation, {
      // headers не додаються, токен вставляє gateway або booking-service
      withCredentials: false,
    });
  }
}
