import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from './core/guards/authentication.guard';
import { AuthorizationGuard} from './core/guards/authorization.guard';

const routes: Routes = [
  {
    // Charge le module Public (Login, Not-authorized)
    path: 'public',
    loadChildren: () => import('./features/public/public.module').then(m => m.PublicModule)
  },
  {
    // Charge le module Admin (protégé par le Guard)
    path: 'admin',
    canActivate: [AuthenticationGuard, AuthorizationGuard],
    data: { roles: ['ADMIN'] },
    loadChildren: () => import('./features/admin/admin.module').then(m => m.AdminModule)
  },
  {
    // Charge le module Member (protégé par le Guard)
    path: 'member',
    canActivate: [AuthenticationGuard, AuthorizationGuard],
    data: { roles: ['USER', 'ADMIN'] },
    loadChildren: () => import('./features/member/member.module').then(m => m.MemberModule)
  },
  // Redirections par défaut
  { path: '', redirectTo: 'public/login', pathMatch: 'full' },
  { path: '**', redirectTo: 'public/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
