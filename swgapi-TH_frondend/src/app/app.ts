import { Component, computed, effect, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Header } from './core/layouts/header/header'
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, Header],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  protected readonly title = signal('swgapi-TH');

  // Detectar si la ruta actual es /login
  constructor(private router: Router) {}

  mostrarHeader(): boolean {
    // No mostrar el header en estas rutas
    const rutaActual = this.router.url;
    return !['/login', '/registro'].includes(rutaActual);
  }
}