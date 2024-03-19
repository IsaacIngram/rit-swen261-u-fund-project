import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  usernameValue: string = ""
  password: string = ""

  login(username: string, password: string): void{
    console.log(username)
    console.log(password)
    
  }
    
  
}
