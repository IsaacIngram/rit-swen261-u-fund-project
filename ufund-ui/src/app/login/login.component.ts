import { Component, inject } from '@angular/core';
import { AccessControlService } from '../access-control.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  private loginService = inject(AccessControlService)
  returncode:number = 0
  charErrorVisible: boolean = false
  passwordCharErrorVisible: boolean = false
  badCredentials: boolean = false
  constructor(private router: Router){
  }

  login(username: HTMLInputElement, password: HTMLInputElement): void{
    this.returncode = this.loginService.login(username.value, password.value)
    if(this.returncode == 0){
      this.charErrorVisible = true
      this.passwordCharErrorVisible = false
      this.badCredentials = false
    }else if(this.returncode == 4){
      this.charErrorVisible = false
      this.passwordCharErrorVisible = true
      this.badCredentials = false
    }else if(this.returncode == 1){
      password.value = ""
      this.charErrorVisible = false
      this.passwordCharErrorVisible = false
      this.badCredentials = true
    }else if(this.returncode == 2){
      username.value = ""
      password.value = ""
      this.router.navigate(['/cupboard'])
    }
    
    
  }
    
  
}
