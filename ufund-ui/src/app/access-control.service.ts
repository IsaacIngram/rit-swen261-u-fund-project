import { Injectable, inject } from '@angular/core';
import { LoginService } from './login.service';
import { User } from './User';
import {catchError, Observable, of} from "rxjs";

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

  login(username: string, password: string): Observable<number> {
    return new Observable<number>((observer) => {
      if (username.length > 20 || username.length < 1) {
        observer.error(0); // Invalid username length
      } else if (password.length > 20 || password.length < 1) {
        observer.error(4); // Invalid password length
      } else {
        this.loginService.getUser(username).subscribe({
          next: (newUser) => {
            this.returnedUser = newUser;
            console.log(this.returnedUser?.username);
            console.log(this.returnedUser?.password);
            if (this.returnedUser?.username === "") {
              console.log("BAD USER");
              observer.next(1); // Bad user
            } else if (password !== this.returnedUser?.password) {
              observer.next(1); // Incorrect password
            } else {
              this.setUser(username);
              observer.next(2); // Successful login
            }
            observer.complete(); // Complete the Observable
          },
          error: (error) => {
            observer.error(error); // Pass the error to the Observable
          }
        });
      }
    }).pipe(
      catchError((error) => {
        console.error('An error occurred:', error);
        return of(error); // Return the error as an Observable
      })
    );
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
