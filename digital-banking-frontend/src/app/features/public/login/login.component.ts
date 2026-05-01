import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthService} from "../../../core/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formLogin! : FormGroup;
  constructor(private fb : FormBuilder, private authService : AuthService, private router : Router) { }

  ngOnInit(): void {
    this.formLogin=this.fb.group({
      username : this.fb.control(""),
      password : this.fb.control("")
    })
  }

  handleLogin() {
    let username = this.formLogin.value.username;
    let password = this.formLogin.value.password;

    this.authService.login(username, password).subscribe({
      next : data => {
        console.log(data);
        // 1. On charge le profil (ce qui remplit authService.roles dans le service)
        this.authService.loadProfile(data);

        // 2. Redirection conditionnelle basée sur les rôles de l'utilisateur
        if (this.authService.roles && this.authService.roles.includes('ADMIN')) {
          // S'il est ADMIN, on l'envoie vers la liste des clients
          this.router.navigateByUrl("/admin/customers");
        } else {
          // Sinon (c'est un USER), on l'envoie vers son espace client
          this.router.navigateByUrl("/member/accounts");
        }
      },
      error : err => {
        console.log(err);
      }
    });
  }

}
