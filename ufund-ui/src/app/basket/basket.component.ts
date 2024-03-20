import {Need} from '../Need';
import {Component} from '@angular/core';
import {NeedService} from "../need.service";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {

  static needs: Need[] = [];

  constructor(
    protected needService: NeedService
  ) { }

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

  /**
   * Checkout the basket. Updates all needs on the server to reflect that they have
   * been fulfilled.
   */
  checkoutBasket() {
    // For each need in the basket
    for(let i = 0; i < BasketComponent.needs.length; i++) {
      const need = BasketComponent.needs[i];

      // Make a request to get the need from the server, and proceed to checkout the need
      // once this request is fulfilled. This allows calculating the new quantity and also
      // enables checking if the need still exists
      this.needService.getNeed(need.id).subscribe(
        cupboard_need => this.checkoutNeed(need, cupboard_need.quantity, need.quantity)
      );
    }
    this.clearBasket();
  }

  /**
   * Checkout an individual need
   * @param need The need to checkout
   * @param totalQuantity The total quantity for the need (quantity in cupboard)
   * @param checkoutQuantity The amount that is being checked out (ie. to remove from cupboard)
   * @private
   */
  private checkoutNeed(need: Need, totalQuantity: number, checkoutQuantity: number) {

    //TODO handle need not existing

    console.log(`BasketComponent: checkoutNeed id=${need.id} total=${totalQuantity} checkout=${checkoutQuantity}`)
    if(checkoutQuantity >= totalQuantity) {
      // Checking out all/more than the number needed, so remove the need
      this.needService.deleteNeed(need.id).subscribe();

      //TODO add error or message of some kind for checking out more than needed
    } else {
      need.quantity = totalQuantity - checkoutQuantity;
      this.needService.updateNeed(need).subscribe();
    }
  }
}
