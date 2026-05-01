import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
// Ajustez ce chemin selon l'emplacement exact de votre AuthService
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  handleLogout(): void {
    // Appelle directement la méthode logout() de VOTRE service
    this.authService.logout();
  }
}
