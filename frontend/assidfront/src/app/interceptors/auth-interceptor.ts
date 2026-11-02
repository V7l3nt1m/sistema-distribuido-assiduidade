import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  // Confirmar que estamos no navegador
  if (typeof window === 'undefined') {
    return next(req);
  }

  const token = localStorage.getItem('token');

  // Se não existir token, envia o pedido normalmente
  if (!token) {
    return next(req);
  }

  // Clonar o pedido e adicionar o JWT
  const requestComToken = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });

  return next(requestComToken);
};