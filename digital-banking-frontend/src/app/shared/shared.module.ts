import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

// Importez vos composants partagés (ajustez le chemin si nécessaire)
import { NavbarComponent } from './components/navbar/navbar.component';

@NgModule({
  declarations: [
    NavbarComponent,
  ],
  imports: [
    CommonModule,
    RouterModule // Indispensable pour que les routerLink fonctionnent dans la navbar
  ],
  exports: [
    NavbarComponent, // On l'exporte pour que app.component.html puisse l'utiliser
  ]
})
export class SharedModule { }
