import { Component } from '@angular/core';
import { SharedModule } from '../../shared/shared-module';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [SharedModule, CommonModule],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header {
  nombreUsuario: string = 'Usuario';
  rolUsuario: string = '';

  constructor (private router: Router){}

  ngOnInit(): void {

  }

  logout(): void {

    this.router.navigate(['/login']);
  }
}
