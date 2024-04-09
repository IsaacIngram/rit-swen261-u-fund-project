import { Component, inject } from '@angular/core';
import { AccessControlService } from '../access-control.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {
  authService = inject(AccessControlService)

  charErrorVisible: boolean = false
  passwordCharErrorVisible: boolean = false
  passwordDontMatch: boolean = false
  usernameNotInUse: boolean = false
  passwordChanged: boolean = false

  returncode: number = 0

  constructor(){

  }

  changePassword(username: HTMLInputElement, password: HTMLInputElement, checkPassword: HTMLInputElement) {
    this.authService.changePassword(username.value, password.value, checkPassword.value).subscribe( result =>{
      this.returncode = result
      if(this.returncode == 0){
        this.charErrorVisible = true
        this.passwordCharErrorVisible = false
        this.passwordDontMatch = false
        this.usernameNotInUse = false
        this.passwordChanged = false
      }else if(this.returncode == 1){
        this.charErrorVisible = false
        this.passwordCharErrorVisible = true
        this.passwordDontMatch = false
        this.usernameNotInUse = false
        this.passwordChanged = false
      }else if(this.returncode == 2){
        this.charErrorVisible = false
        this.passwordCharErrorVisible = false
        this.passwordDontMatch = true
        this.usernameNotInUse = false
        this.passwordChanged = false
      }else if(this.returncode == 3){
        this.charErrorVisible = false
        this.passwordCharErrorVisible = false
        this.passwordDontMatch = false
        this.usernameNotInUse = true
        this.passwordChanged = false
      }else if(this.returncode == 4){
        this.charErrorVisible = false
        this.passwordCharErrorVisible = false
        this.passwordDontMatch = false
        this.usernameNotInUse = false
        this.passwordChanged = true
        username.value = ""
        password.value = ""
        checkPassword.value = ""
      }
    })
  }
}
