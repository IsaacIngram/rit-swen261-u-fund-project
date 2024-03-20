import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccessControlService {

  constructor() { 
    this.setUser("");
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
      this.setUser(username.value)
      return 1;
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
