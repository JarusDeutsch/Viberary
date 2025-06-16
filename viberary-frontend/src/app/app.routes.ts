import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './modules/auth/pages/login/login.component';
import { HomeComponent } from './pages/home.component';
import { ProfileComponent } from './components/profile/profile.component'; // ✅ імпортовано

export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'profile', component: ProfileComponent }, // ✅ додано
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];
