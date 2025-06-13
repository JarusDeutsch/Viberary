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
  token?: string;
  access_token?: string;
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
        const token = res.token || res.access_token;
        console.log('🧪 Отримана відповідь від /login:', res);

        if (typeof token === 'string') {
          localStorage.setItem('token', token);
          console.log('🔐 Токен збережено у localStorage:', token);
        } else {
          console.warn('⚠️ Токен не знайдено у відповіді або він некоректний:', token);
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    const token = localStorage.getItem('token');
    console.log('📦 Поточний токен з localStorage:', token);
    return token;
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
