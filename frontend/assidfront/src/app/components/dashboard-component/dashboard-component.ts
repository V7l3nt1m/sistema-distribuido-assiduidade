import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FuncionarioComponent } from '../funcionario-component/funcionario-component';
import { AdminComponent } from '../admin-component/admin-component';
import { RhComponent } from '../rh-component/rh-component';
import { Router } from '@angular/router';

@Component({
  
  imports: [CommonModule, AdminComponent, FuncionarioComponent, RhComponent],
  selector: 'app-dashboard-component',
  styleUrl: './dashboard-component.css',
  templateUrl: './dashboard-component.html',
})
export class DashboardComponent {

  // Dados temporários para desenvolver a interface.
  nomeUtilizador = 'Valentim Prado';
  role = 'ADMIN';

  constructor(private router: Router) {}

  terminarSessao(): void {
    // Substituir pela lógica real de logout na integração
    // com o sistema de autenticação.
    this.router.navigate(['/login']);
  }
}
