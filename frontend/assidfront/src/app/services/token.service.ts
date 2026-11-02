import { Service } from '@angular/core';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

interface TokenPayload {
  sub: string;
  role: string;
  funcionarioId: string;
  exp: number;
}

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  obterDadosToken(): TokenPayload | null {

    // Confirmar que estamos no navegador
  if (typeof window === 'undefined') {
    return null;
  }
  
    const token = localStorage.getItem('token');

    if (!token) {
      return null;
    }

    try {
      return jwtDecode<TokenPayload>(token);
    } catch {
      return null;
    }
  }
}