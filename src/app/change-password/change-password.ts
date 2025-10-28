import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './change-password.html',
  styleUrls: ['./change-password.css'],
})
export class ChangePassword {
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';

  onSubmit() {
    if (!this.currentPassword || !this.newPassword || !this.confirmPassword) {
      alert('Completa todos los campos.');
      return;
    }
    if (this.newPassword !== this.confirmPassword) {
      alert('Las contraseñas no coinciden.');
      return;
    }
    alert('Contraseña actualizada (simulado).');
    // opcional: redirigir al login:
    // this.router.navigate(['/login']);
  }
}
