import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

export interface Book {
  id: number;
  title: string;
  author: string;
  genre: string;
  description: string;
  demoUrl: string;
  totalCopies: number;
  availableCopies: number;
  available: boolean;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private baseUrl = '/api/books';


  constructor(private http: HttpClient) {}

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.baseUrl).pipe(
      catchError(error => {
        console.error('❌ Помилка при отриманні книг:', error);
        return throwError(() => error);
      })
    );
  }




  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/${id}`).pipe(
      catchError(error => {
        console.error(`❌ Помилка при отриманні книги з ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }
}
