import { Injectable, inject } from '@angular/core';
import { LoginService } from './login.service';
import { User } from './User';

@Injectable({
  providedIn: 'root'
})
export class AccessControlService {
  private loginService = inject(LoginService)
  returnedUser: User = {
    username: "",
    password: ""
  }

  constructor() {
    this.setUser("");
  }

  createAccount(username: string, password: string): number{
    this.loginService.getUser(username).subscribe(newUser => this.returnedUser = newUser )
    if(this.returnedUser.username == ""){
      return 0
    }else{
      this.loginService.createUser(this.returnedUser)
      return 1
    }
  }


  private getUser(): string | null{
    return localStorage.getItem("user");
  }

  private setUser(name: string): void {
    localStorage.setItem("user", name);
  }

  login(username: HTMLInputElement, password: HTMLInputElement): number{
    if(username.value.length > 20 || username.value.length < 1){
      return 0;
    }else{
      this.loginService.getUser(username.value).subscribe(newUser => this.returnedUser = newUser )
      if(this.returnedUser.username == ""){
        return 1
      }
      if(password.value != this.returnedUser.password){
        return 1
      }else{
        this.setUser(username.value)
        return 2
      }
    }
  }
  
  logout(): void{
    this.setUser("")
  }

  isAdmin(): boolean {
    if(this.getUser() == "admin"){
      return true;
    }else{
      return false;
    }
  }

  isHelper(): boolean {
    if(this.getUser() != "admin" && this.getUser() != ""){
      return true;
    }else{
      return false;
    }
  }

}
