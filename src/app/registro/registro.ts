import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css'],
})
export class Registro {
  // campos de ejemplo
  usuario = '';
  correo = '';
  password = '';
  confirm = '';

  onSubmit() {
    if (!this.usuario || !this.correo || !this.password || !this.confirm) {
      alert('Por favor completa todos los campos.');
      return;
    }
    if (this.password !== this.confirm) {
      alert('Las contraseñas no coinciden.');
      return;
    }
    alert('Registro simulado OK.');
    // this.router.navigate(['/login']);
  }
}
