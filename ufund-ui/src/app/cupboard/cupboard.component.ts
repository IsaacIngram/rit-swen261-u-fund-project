import { Component } from '@angular/core';
import { AccessControlService } from "../access-control.service";

@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrl: './cupboard.component.css'
})
export class CupboardComponent {

  constructor(protected accessControl: AccessControlService) { }

}
