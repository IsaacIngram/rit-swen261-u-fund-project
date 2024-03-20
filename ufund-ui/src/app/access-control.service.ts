import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccessControlService {

  constructor() { }

  isAdmin(): boolean {
    return true;
  }

  isHelper(): boolean {
    return true;
  }

}
