import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.errorMessage = null;

    this.authService.logout(); // очищення перед новою спробою

    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: (response) => {
        const token = response.token || response.access_token;
        if (token) {
          localStorage.setItem('token', token);
          this.router.navigate(['/']);
        } else {
          this.errorMessage = 'Сервер не надіслав токен';
          console.warn('⚠️ Токен відсутній у відповіді:', response);
        }
      },
      error: (err) => {
        this.errorMessage = 'Невірний email або пароль';
        console.error('Login error:', err);
      }
    });
  }
}
