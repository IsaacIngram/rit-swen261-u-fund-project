import { Component, inject } from '@angular/core';
import { AccessControlService } from '../access-control.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrl: './create-account.component.css'
})
export class CreateAccountComponent {
  private authService = inject(AccessControlService)

  charErrorVisible: boolean = false
  nameTaken: boolean = false
  passwordDontMatch: boolean = false

  createaccount(login: HTMLInputElement, password: HTMLInputElement, checkpassword: HTMLInputElement){
    if(password.value != checkpassword.value){
      this.passwordDontMatch = true
      return
    }
    
  }
}
