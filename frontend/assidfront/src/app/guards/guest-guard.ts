import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

export const guestGuard: CanActivateFn = () => {

  const tokenService = inject(TokenService);
  const router = inject(Router);

  const dados = tokenService.obterDadosToken();

  // Se existir um token válido e não expirado
  if (dados && dados.exp * 1000 > Date.now()) {
    return router.createUrlTree(['/dashboard']);
  }

  // Se não estiver autenticado, permite aceder ao Login
  return true;
};


