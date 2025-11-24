import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const publicRoutes = ['login', 'registro', 'change-password'];
    const currentPath = route.routeConfig?.path;

    // ✅ Si la ruta es pública, permitir acceso
    if (currentPath && publicRoutes.includes(currentPath)) {
      return true;
    }

    // ✅ Para rutas protegidas, validar token
    const token = localStorage.getItem('token');
    if (token) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}