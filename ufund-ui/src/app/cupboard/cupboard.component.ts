import { Component } from '@angular/core';
import { AccessControlService } from "../access-control.service";
import {Need} from "../Need";
import { NgFor } from '@angular/common';
import {NeedService} from "../need.service";
import { NgFor } from '@angular/common';

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
   * Add a new need to the server
   * @param name need name
   * @param type need type
   * @param price need price
   * @param quantity need quantity
   */
  addNeed(name: string, type: string, price: number, quantity: number) {
    const newNeed = {
      id: -1, name: name, type: type, quantity: quantity, price: price
    };
    this.needService.addNeed(newNeed).subscribe(_ => this.getNeeds());
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
    this.needService.updateNeed(newNeed).subscribe();
  }

  /**
   * Remove a need from the server, so it can't be placed in any baskets
   * @param id need if
   */
  removeNeed(id: number) {
    this.needService.deleteNeed(id).subscribe(_ => this.getNeeds());
  }

  searchString(searchString: string) {
    if(searchString == '') {
      // Empty search string, show all needs
      this.getNeeds()
    } else {
      // Make REST request to search for needs with this string. When we get a response, update
      // the needs being displayed
      this.needService.searchNeeds(searchString).subscribe(needs => this.needs = needs)
    }
  }

  add(name: string) {
    name = name.trim();
    if(!name){
      return;
      }
    }

}
