import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './modules/auth/pages/login/login.component';
import { HomeComponent } from './pages/home.component';
import {UserDashboardComponent} from "./components/user-dashboard/user-dashboard.component";

export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'dashboard', component: UserDashboardComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];
