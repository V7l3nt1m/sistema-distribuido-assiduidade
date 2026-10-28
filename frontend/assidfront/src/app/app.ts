import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginComponent } from './components/login-component/login-component';
import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { ErrorComponent } from './components/error-component/error-component';
import { FuncionarioComponent } from './components/funcionario-component/funcionario-component';
import { AdminComponent } from './components/admin-component/admin-component';
import { RhComponent } from './components/rh-component/rh-component';


@Component({
  imports: [RouterOutlet, LoginComponent, DashboardComponent, ErrorComponent, FuncionarioComponent, AdminComponent, RhComponent],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('assidfront');
}
