import { Component } from '@angular/core';
import { AccessControlService } from "../access-control.service";
import {Need} from "../Need";
import {NeedService} from "../need.service";

@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrl: './cupboard.component.css'
})
export class CupboardComponent {


  needs: Need[] = []

  constructor(
    protected accessControl: AccessControlService,
    protected needService: NeedService) { }

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
    // Subscribe to the service to get needs
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  /**
   * Save a needs content from the webpage
   * @param oldNeed Need being saved
   * @param newName New name (from input box)
   * @param newType New type (from input box)
   * @param newQuantity New quantity (from input box)
   * @param newPrice New price (from input box)
   */
  saveNeed(oldNeed: Need, newName: string, newType: string, newQuantity: number, newPrice: number): void {
    // Create need with new data and old ID
    const newNeed: Need = {
      id: oldNeed.id, name: newName, type: newType, quantity: newQuantity, price: newPrice
    };
    // Tell the service to update the need
    this.needService.updateNeed(newNeed).subscribe(updatedNeed => {
      console.log("updated need");
    });
  }

}
