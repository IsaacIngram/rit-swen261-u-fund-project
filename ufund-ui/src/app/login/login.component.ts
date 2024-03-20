import { Component, inject } from '@angular/core';
import { AccessControlService } from '../access-control.service'; 

import { AppModule } from '../app.module';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  private loginService = inject(AccessControlService)
  returncode:number = 0
  charErrorVisible: boolean = false
  constructor(){

  }

  login(username: HTMLInputElement, password: HTMLInputElement): void{
    this.returncode = this.loginService.login(username, password)
    if(this.returncode == 0){
      this.charErrorVisible = true
    }else if(this.returncode == 1){

      username.value = ""
      password.value = ""
    }
    
    
  }
    
  
}
