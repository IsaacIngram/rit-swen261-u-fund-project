import { Component } from '@angular/core';
import { AccessControlService } from './access-control.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ufund-ui';

  constructor( private accessService: AccessControlService,
               private router: Router){

  }

  isLoggedIn(): boolean{
    return this.accessService.isAdmin() || this.accessService.isHelper()
  }

  logout(): void{
    this.accessService.logout()
    this.router.navigate(['/login'])
  }
}
