import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
//import jwt_decode from 'jwt-decode';
import {jwtDecode} from "jwt-decode";
// Import de nos nouveaux modèles
import { LoginRequest } from '../../models/login-request.model';
import { AuthResponse } from '../../models/auth-response.model';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private backendHost = environment.backendHost;

  public username: string | undefined;
  public roles: string[] = [];
  public isAuthenticated: boolean = false;
  public accessToken: string | undefined;

  constructor(private http: HttpClient, private router: Router) { }


  // 1. La méthode d'appel API ultra propre
  public login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.backendHost}/auth/login`, request);
  }

  // 2. La méthode pour décoder et stocker
  public loadProfile(data: AuthResponse) {
    this.isAuthenticated = true;
    this.accessToken = data.accessToken;
    window.localStorage.setItem("jwt-token", this.accessToken);

    let decodedJwt: any = jwtDecode(this.accessToken);
    this.username = decodedJwt.sub;
    // Si le scope est une chaîne "SCOPE_ADMIN SCOPE_USER", on la découpe en tableau
    if (decodedJwt.scope) {
      this.roles = decodedJwt.scope.split(' ').map((role: string) => {
        return role.replace('SCOPE_', '').replace('ROLE_', '');
      });

      console.log("Rôles nettoyés par Angular :", this.roles);
    } else {
      this.roles = [];
    }
  }



  loadJwtTokenFromLocalStorage() {
    let jwtToken = window.localStorage.getItem("jwt-token");
    if (jwtToken) {
      // 1. On recharge le profil depuis le token
      this.loadProfile({accessToken : jwtToken});

      // 2. On applique la même logique de redirection conditionnelle ici
      if (this.roles && this.roles.includes('ADMIN')) {
        this.router.navigateByUrl("/admin/customers");
      } else {
        this.router.navigateByUrl("/member/accounts");
      }
    }
  }

  logout() {
    // 1. On vide le localStorage
    window.localStorage.removeItem("access-token");
    // 2. On réinitialise les variables d'état du service
    this.isAuthenticated = false;
    this.username = undefined;
    this.roles = [];
    this.accessToken = undefined;

    this.router.navigateByUrl("/public/login");
  }
}
