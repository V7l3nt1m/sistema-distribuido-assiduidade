import { Routes } from '@angular/router';
import { LoginComponent } from './components/login-component/login-component';
import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { ErrorComponent } from './components/error-component/error-component';


export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'error', component: ErrorComponent},
    {path: '', redirectTo:'login',pathMatch: 'full'},
    { path: '**', redirectTo: 'error' }
];
