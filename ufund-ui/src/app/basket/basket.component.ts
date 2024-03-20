import { Need } from '../Need';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {

  static needs: Need[] = [];
  /**
   * Adds an item to the basket
   * @param need the item being added to the basket
   */
  static addToBasket(need: Need): void {
    BasketComponent.needs.push(need);
  }
  /**
   * Creates an empty basket
   */
  clearBasket() {
    BasketComponent.needs = [];
  }
  /**
   * Get all needs that are in the basket
   */
  getBasket(): Need[] {
    return BasketComponent.needs;
  }
  /**
   * Adjusts the quantity of each need in the basket
   * @param need the need that the quanity is being adjusted of
   * @param number either 1 or -1 depending on if a need is being increased or decreased
   */
  adjustQuantity(need: Need, delta: number): void {
    need.quantity+=delta;
    // stops need from going below at least 1 in the basket
    if(need.quantity < 1) {
      need.quantity = 1;
    }
  }
  /**
   * Removes a need from the basket
   *@param need the need that is being removed 
   */
  removeFromBasket(need: Need): void {
    BasketComponent.needs = BasketComponent.needs.filter(n => n !== need);
  }
}