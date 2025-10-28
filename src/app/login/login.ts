import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {
  usuario = '';
  password = '';
  remember = false;

  constructor(private router: Router) {}

  onSubmit() {
    if (!this.usuario || !this.password) {
      alert('Por favor ingresa usuario y contraseña.');
      return;
    }

    // Simulación de login exitoso
    this.router.navigate(['/cuenta-ahorros']);
  }
}
