import { Injectable } from '@angular/core';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class AccessControlService {

  constructor() {
    this.setUser("");
  }

  createAccount(username: string, password: string): number{
    
    return 0
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
    }else if(false){
      return 1;
    }else{
      this.setUser(username.value)
      return 2
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
