import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

export const authGuard: CanActivateFn = () => {

  const tokenService = inject(TokenService);
  const router = inject(Router);

  const dados = tokenService.obterDadosToken();

  // Verificar se existe token válido e não expirado
  if (dados && dados.exp * 1000 > Date.now()) 
  {
    return true;
  }

  if (typeof window !== 'undefined') 
    {
  localStorage.removeItem('token');
  }

  // Redireccionar para o login
  return router.createUrlTree(['/login']);
};
