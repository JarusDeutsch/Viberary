import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
}

export interface AuthRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  access_token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:9000/api/auth';

  constructor(private http: HttpClient) {}

  register(data: RegisterRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  login(data: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, data).pipe(
      tap(res => {
        // Записуємо токен у сховище
        localStorage.setItem('token', res.access_token);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
