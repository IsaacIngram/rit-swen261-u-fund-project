import { Injectable, inject } from '@angular/core';
import { LoginService } from './login.service';
import { User } from './User';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AccessControlService {
  private loginService = inject(LoginService)
  returnedUser: User = {
    username: "a",
    password: "a"
  }
  newUser: User = {
    username: "a",
    password: "a",
  }

  constructor() {
    this.setUser("");
  }

  createAccount(username: string, password: string): Observable<number> {
    return new Observable<number>((observer) => {
      this.loginService.getUser(username).subscribe({
        next: (data) => {
          this.returnedUser = data;
          if (this.returnedUser.username !== "") {
            observer.next(0); // User exists, return 0
            observer.complete(); // Complete the Observable
          } else {
            this.newUser.username = username;
            this.newUser.password = password;
            this.loginService.createUser(this.newUser).subscribe(() => {
              observer.next(1); // User created, return 1
              observer.complete(); // Complete the Observable
            });
          }
        },
        error: (error) => {
          observer.error(error); // Pass the error to the Observable
        }
      });
    });
  }


  private getUser(): string | null{
    return localStorage.getItem("user");
  }

  private setUser(name: string): void {
    localStorage.setItem("user", name);
  }

  login(username: string, password: string): number{
    if(username.length > 20 || username.length < 1){
      return 0
    }else if(password.length > 20 || password.length < 1){
      return 4
    }else{
      this.loginService.getUser(username).subscribe(newUser => this.returnedUser = newUser)
      console.log(this.returnedUser.username)
      console.log(this.returnedUser.password)
      if(this.returnedUser.username == ""){
        console.log("BAD USER")
        return 1
      }
      if(password != this.returnedUser.password){
        return 1
      }else{
        this.setUser(username)
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
