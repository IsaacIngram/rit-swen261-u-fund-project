import { Injectable, inject } from '@angular/core';
import { LoginService } from './login.service';
import { User } from './User';
import { Observable, catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccessControlService {
  private loginService = inject(LoginService)
  returnedUser: User = {
    username: "",
    password: ""
  }
  newUser: User = {
    username: "",
    password: "",
  }

  constructor() {
    this.setUser("");
  }

  createAccount(username: string, password: string): number{
    this.loginService.getUser(username).subscribe(newUser => this.returnedUser = newUser )
    if(this.returnedUser.username !== ""){
      return 0
    }else{
      this.newUser.username = username
      this.newUser.password = password
      this.loginService.createUser(this.newUser).subscribe()
      return 1
    }
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
      this.loginService.getUser(username).subscribe(newUser => this.returnedUser = newUser )
      if(this.returnedUser.username == ""){
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

  changePassword(username: string, newPassword: string, newPasswordCheck: string): Observable<number> {
    return new Observable<number>((observer) => {
      if (username.length > 20 || username.length < 1) {
        observer.error(0); // Invalid username length
      } else if (newPassword.length > 20 || newPassword.length < 1) {
        observer.error(1); // Invalid password length
      } else if( newPassword !== newPasswordCheck){
        observer.error(2) // Passwords don't match
      } else{
        this.loginService.getUser(username).subscribe({ 
          next: (newUser) => {
            this.returnedUser = newUser
            if (this.returnedUser.username === "") {
              observer.next(3); // Bad user
            }else{
              this.returnedUser.password = newPassword
              this.loginService.changePassword(this.returnedUser).subscribe()
            }
            observer.complete()
          },
          error: (error) => {
            observer.error(error); // Pass the error to the Observable
          }
        })
      }
      
    }).pipe(
      catchError((error) => {
        console.error('An error occurred:', error);
        return of(error); // Return the error as an Observable
      })

    )

    
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
