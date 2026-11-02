import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FuncionarioComponent } from '../funcionario-component/funcionario-component';
import { AdminComponent } from '../admin-component/admin-component';
import { RhComponent } from '../rh-component/rh-component';
import { Router } from '@angular/router';
import { TokenService } from '../../services/token.service';
import { inject } from '@angular/core';

@Component({
  
  imports: [CommonModule, AdminComponent, FuncionarioComponent, RhComponent],
  selector: 'app-dashboard-component',
  styleUrl: './dashboard-component.css',
  templateUrl: './dashboard-component.html',
})
export class DashboardComponent {

  private tokenService = inject(TokenService);

  username: string = '';
  role: string = '';
  funcionarioId: string = '';

  ngOnInit(): void {
    const dados = this.tokenService.obterDadosToken();

  if (dados) {
    this.username = dados.sub;
    this.role = dados.role;
    this.funcionarioId = dados.funcionarioId;
  }
  }


  constructor(private router: Router) {}

  terminarSessao(): void {
    // Substituir pela lógica real de logout na integração
    // com o sistema de autenticação.
    this.router.navigate(['/login']);
  }
}
