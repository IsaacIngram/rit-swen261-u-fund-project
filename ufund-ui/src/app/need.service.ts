import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, of, tap} from "rxjs";
import {Need} from "./Need";

@Injectable({
  providedIn: 'root'
})
export class NeedService {

  // URL to web API
  private needsEndpoint = 'http://localhost:8080/needs'

  // Use JSON content type
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  /**
   * Get all needs from server
   * @return Observable<Need[]> all needs
   */
  getNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.needsEndpoint)
      .pipe(
        tap(_ => this.log('fetched needs')),
        catchError(this.handleError<Need[]>('getNeeds', []))
      );
  }

  /**
   * Get need with ID from server
   * @param id id of need
   * @return Observable<Need[]> requested need if it exists
   */
  getNeed(id: number): Observable<Need> {
    const url = `${this.needsEndpoint}/${id}`;
    return this.http.get<Need>(url).pipe(
      tap(_ => this.log(`fetched need id=${id}`)),
      catchError(this.handleError<Need>(`getNeed id=${id}`))
    )
  }

  /**
   * Search for needs with a term on the server
   * @param term term to search for
   * @return Observable<Need[]> needs matching the query
   */
  searchNeeds(term: string): Observable<Need[]> {
    if(!term.trim()) {
      // If not a search term, return empty hero array
      return of([]);
    }
    const url: string = `${this.needsEndpoint}/?name=${term}`
    return this.http.get<Need[]>(url).pipe(
      tap(_ => this.log(`searched needs term=${term}`)),
      catchError(this.handleError<Need[]>(`searchNeeds term=${term}`))
    )
  }

  /**
   * Add a new need to the server
   * @param need Need object to add
   * @return Observable<Need> need that was added to the server
   */
  addNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(this.needsEndpoint, need, this.httpOptions).pipe(
      tap((newNeed: Need) => this.log(`added need with id=${newNeed.id}`)),
      catchError(this.handleError<Need>('addNeed'))
    );
  }

  /**
   * Delete a need from the server
   * @param id id of the need to delete
   * @return Observable<Need[]> deleted Need
   */
  deleteNeed(id: number): Observable<Need> {
    const url = `${this.needsEndpoint}/${id}`;

    return this.http.delete<Need>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted need id=${id}`)),
      catchError(this.handleError<Need>('deleteNeed'))
    );
  }

  /**
   * Update a need on the server
   * @param need Need with updated content
   * @return Observable<any> Updated need
   */
  updateNeed(need: Need): Observable<any> {
    return this.http.put(this.needsEndpoint, need, this.httpOptions).pipe(
      tap(_ => this.log(`updated need id=${need.id}`)),
      catchError(this.handleError<any>('updateNeed'))
    )
  }

  /**
   * Log a message
   * @param message message to log
   */
  private log(message: string) {
    console.log(`NeedService: ${message}`);
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
