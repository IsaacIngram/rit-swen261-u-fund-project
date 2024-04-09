import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './User';
import { Observable, catchError, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  // URL to web API
  private loginEndpoint = 'http://localhost:8080/auth'

  // Use JSON content type
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  getUser(username: string): Observable<User>{
    const url = `${this.loginEndpoint}/${username}`;
    return this.http.get<User>(url)
    .pipe(
      tap(_ => this.log(`fetched user username=${username}`)),
      catchError(this.handleError<User>(`getUser username=${username}`))
    )
  }

  createUser(user: User): Observable<User>{
    return this.http.post<User>(this.loginEndpoint, user, this.httpOptions).pipe(
      tap((newUser: User) => this.log(`added user with username=${newUser.username}`)),
      catchError(this.handleError<User>('addUser'))
    );
  }

  deleteUser(username: string): Observable<User>{
    const url = `${this.loginEndpoint}/${username}`;

    return this.http.delete<User>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted user with username=${username}`)),
      catchError(this.handleError<User>('deleteUser'))
    );
  }

  changePassword(user: User): Observable<User>{
    return this.http.put<User>(this.loginEndpoint, user, this.httpOptions).pipe(
      tap((newUser: User) => this.log(`changed password of user with username=${newUser.username}`)),
      catchError(this.handleError<any>('changePassword'))
    );
  }

  private log(message: string) {
    console.log(`LoginService: ${message}`);
  }

  private handleError<T>(operation: string = 'operation', result? : T) {
    return (error: any): Observable<T> => {
      // Log error to console
      console.error(error);

      // Let the app keep running by returning an empty result
      return of(result as T);
    }
  }
}
