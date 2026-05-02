import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { LoginRequest } from '../../../models/login-request.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formLogin!: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Initialisation du formulaire
    this.formLogin = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  handleLogin() {
    // Si le formulaire est vide ou invalide, on ne fait rien
    if (this.formLogin.invalid) return;

    // On extrait les valeurs du formulaire pour créer notre objet requête
    const request: LoginRequest = this.formLogin.value;

    this.authService.login(request).subscribe({
      next: (response) => {
        // 1. On charge le token en mémoire
        this.authService.loadProfile(response);

        // 2. Redirection conditionnelle (Vérifiez si c'est SCOPE_ADMIN ou juste ADMIN selon votre backend)
        if (this.authService.roles.includes('ADMIN')) {
          this.router.navigateByUrl("/admin/customers");
        } else {
          this.router.navigateByUrl("/member/accounts");
        }

      },
      error: (err) => {
        this.errorMessage = "Nom d'utilisateur ou mot de passe incorrect.";
        console.error(err);
      }
    });
  }
}
