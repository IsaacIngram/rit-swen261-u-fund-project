import { Component, inject } from '@angular/core';
import { AccessControlService } from '../access-control.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrl: './create-account.component.css'
})
export class CreateAccountComponent {
  private authService = inject(AccessControlService)
  returnVal: number = 0

  charErrorVisible: boolean = false
  nameTaken: boolean = false
  passwordDontMatch: boolean = false

  constructor(private router: Router){

  }

  createaccount(username: HTMLInputElement, password: HTMLInputElement, checkpassword: HTMLInputElement){
    if(username.value.length > 20 || username.value.length < 1){
      this.passwordDontMatch = false
      this.charErrorVisible = true
      this.nameTaken = false
    }
    if(password.value != checkpassword.value){
      this.passwordDontMatch = true
      this.charErrorVisible = false
      this.nameTaken = false
      return
    }
    this.returnVal = this.authService.createAccount(username.value, password.value)
    if(this.returnVal == 1){
      this.router.navigate(['/cupboard'])
    }else{
      this.passwordDontMatch = false
      this.charErrorVisible = false
      this.nameTaken = true
    }

  }
}
