import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationGuard implements CanActivate {

  constructor(private authService : AuthService, private router : Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    // 1. On récupère les rôles exigés par la route actuelle (ex: ['USER', 'ADMIN'])
    const requiredRoles = route.data['roles'] as Array<string>;

    // S'il n'y a pas de restriction spécifique sur la route, on laisse passer
    if (!requiredRoles || requiredRoles.length === 0) {
      return true;
    }

    // 2. On vérifie si l'utilisateur possède au moins un des rôles requis
    const hasAccess = requiredRoles.some(role => this.authService.roles.includes(role));

    if (hasAccess) {
      return true; // La porte s'ouvre !
    } else {
      // 3. Accès refusé : on redirige vers la page non-autorisé
      // J'ai mis /public/not-authorized car j'ai vu que ton composant y était déclaré
      this.router.navigateByUrl("/public/not-authorized");
      return false;
    }
  }
}
