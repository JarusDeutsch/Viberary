import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

interface UserProfile {
  id: number;
  name: string;
  email: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8081/api/users';

  constructor(private http: HttpClient) {}

  getProfileByEmail(email: string): Observable<UserProfile> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<UserProfile>(`${this.baseUrl}/by-email?email=${email}`, { headers });
  }
}
