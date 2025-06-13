import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card'; // <-- додано
import { AuthService, RegisterRequest } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule // <-- додано
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  name = '';
  email = '';
  password = '';

  constructor(private authService: AuthService) {}

  register() {
    const request: RegisterRequest = {
      name: this.name,
      email: this.email,
      password: this.password
    };

    this.authService.register(request).subscribe({
      next: () => alert('Успішна реєстрація'),
      error: (err) => alert('Помилка: ' + err.error?.message || err.message)
    });
  }
}
