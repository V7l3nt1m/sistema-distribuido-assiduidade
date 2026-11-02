import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AutenticacaoService } from '../../services/autenticacao.service';
import {Router} from '@angular/router';

@Component({
  imports: [FormsModule],
  selector: 'app-login-component',
  styleUrl: './login-component.css',
  templateUrl: './login-component.html',
})
export class LoginComponent {
  private authService = inject(AutenticacaoService);
  private router = inject(Router);
  username: string = '';
  password: string = '';

  iniciarSessao(): void 
  {
    this.authService.login(this.username, this.password)
      .subscribe({
        next: (resposta) => {
          console.log("Feito");
          console.log('jwt recebido: ', resposta.accessToken);
          localStorage.setItem('token', resposta.accessToken);
          this.router.navigate(["/dashboard"]);
        },
        error: (erro) => {
          console.log("erro: ",erro);
        }
      })
    
  }
}
