import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './navbar/navbar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Navbar, RouterOutlet],
  template: `
    <app-navbar></app-navbar>
    <main style="padding: 1.25rem;">
      <router-outlet></router-outlet>
    </main>
  `
})
export class AppRoot {}
