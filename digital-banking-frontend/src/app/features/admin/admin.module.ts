import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

// Mes composants
import { CustomersComponent } from './customers/customers.component';
import { NewCustomerComponent } from './new-customer/new-customer.component';
import { CustomerAccountsComponent } from './customer-accounts/customer-accounts.component';

// Les routes internes du module Admin
const routes: Routes = [
  { path: 'customers', component: CustomersComponent },
  { path: 'new-customer', component: NewCustomerComponent },
  { path: 'customer-accounts/:id', component: CustomerAccountsComponent },
  { path: '', redirectTo: 'customers', pathMatch: 'full' }
];

@NgModule({
  declarations: [
    CustomersComponent,
    NewCustomerComponent,
    CustomerAccountsComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forChild(routes) // Branchement des routes du module
  ]
})
export class AdminModule { }
