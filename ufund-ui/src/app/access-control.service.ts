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

  login(name: string): boolean{
    if(name == ""){
      return false;
    }else{
      this.setUser(name);
      /* NEED TO WRITE CODE TO REDIRECT USER TO THE CORRECT PAGE*/
      return true;
    }
  }
  logout(): void{

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
