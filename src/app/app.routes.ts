import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Registro } from './registro/registro';
import { ChangePassword } from './change-password/change-password';
import { FormularioCuentaAhorros } from './formulario-cuenta-ahorros/formulario-cuenta-ahorros';
import { FormularioCuentaCorriente } from './formulario-cuenta-corriente/formulario-cuenta-corriente';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'registro', component: Registro },
  { path: 'change-password', component: ChangePassword },
  { path: 'cuenta-ahorros', component: FormularioCuentaAhorros },
  { path: 'cuenta-corriente', component: FormularioCuentaCorriente },
  // opcional: ruta comodín
  { path: '**', redirectTo: 'login' }
];
