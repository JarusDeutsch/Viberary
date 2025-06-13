import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

// 🔽 імпортуємо потрібні Material модулі
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'] // ✅ styleUrls (не styleUrl)
})
export class AppComponent {
  title = 'viberary-frontend';
}
