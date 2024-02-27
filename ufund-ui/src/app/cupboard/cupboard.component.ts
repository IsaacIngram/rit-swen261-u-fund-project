import { Component } from '@angular/core';
import { AccessControlService } from "../access-control.service";
import {Need} from "../Need";

@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrl: './cupboard.component.css'
})
export class CupboardComponent {


  needs: Need[] = []

  constructor(protected accessControl: AccessControlService) { }

  /**
   * Built into Angular. Called when Angular is done initializing this
   * component
   */
  ngOnInit(): void {
    this.getNeeds();
  }

  /**
   * Get all needs in the cupboard. Makes REST request to get all Needs from
   * the server.
   */
  getNeeds(): void {
    //TODO link to needs service
    // For now, this just
    this.needs = [
      {id: 1, name: "water", type: "BEVERAGE", quantity: 3, price: 2.98},
      {id: 2, name: "hamburger", type: "FOOD", quantity: 9, price: 12.34},
      {id: 3, name: "$1000 fund", type: "MONETARY", quantity: 1000, price: 1.00}
    ]
  }

}
