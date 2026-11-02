import { Routes } from '@angular/router';
import { LoginComponent } from './components/login-component/login-component';
import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { ErrorComponent } from './components/error-component/error-component';
import { authGuard } from './guards/auth-guard';
import { guestGuard } from './guards/guest-guard';


export const routes: Routes = [
    {path: 'login', component: LoginComponent, canActivate: [guestGuard]},
    {path: 'dashboard', component: DashboardComponent, canActivate:[authGuard]},
    {path: 'error', component: ErrorComponent},
    {path: '', redirectTo:'login',pathMatch: 'full'},
    { path: '**', redirectTo: 'error' }
];
