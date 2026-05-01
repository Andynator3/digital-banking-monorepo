import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

// Mon composant
import { AccountsComponent } from './accounts/accounts.component';

// Les routes internes du module Member
const routes: Routes = [
  { path: 'accounts', component: AccountsComponent },
  { path: '', redirectTo: 'accounts', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    AccountsComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forChild(routes) // Branchement des routes du module
  ]
})
export class MemberModule { }
