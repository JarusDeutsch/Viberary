import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

export interface Booking {
  id: number;
  bookId: number;
  startDate: string;
  endDate: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private baseUrl = '/api/bookings';

  constructor(private http: HttpClient) {}

  getUserBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.baseUrl).pipe(
      catchError(error => {
        console.error('❌ Помилка при отриманні бронювань:', error);
        return throwError(() => error);
      })
    );
  }

  createReservation(reservation: {
    bookId: number;
    startDate: string;
    endDate: string;
  }): Observable<void> {
    return this.http.post<void>(this.baseUrl, reservation);
  }

  markAsFinished(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/finish`, null);
  }
}
