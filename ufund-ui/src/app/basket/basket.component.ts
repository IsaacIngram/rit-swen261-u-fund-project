import {Need} from '../Need';
import {Component} from '@angular/core';
import {NeedService} from "../need.service";
import {AccessControlService} from "../access-control.service";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {

  static needs: Need[] = [];
  static currentInstance: BasketComponent;

  constructor(
    protected needService: NeedService,
    protected accessControlService: AccessControlService
  ) { 
    BasketComponent.currentInstance = this;
  }

  ngOnInit(): void {
    BasketComponent.currentInstance = this;
    this.loadUserBasket();
  }

  loadUserBasket(): void {
    this.needService.getNeeds().subscribe(list => {
      BasketComponent.needs = list.filter(element => {
        const currentUserName = localStorage.getItem("user");
        if(currentUserName != null) {
          return currentUserName in element.userBaskets;
        }
        return false;
      })
    })
  }

  /**
   * Adds an item to the basket
   * @param need the item being added to the basket
   */
  static addToBasket(need: Need, quantity: number): void {
    const currentUserName = localStorage.getItem("user");
    if(currentUserName != null) {
      if(need) {
        need.userBaskets[currentUserName] = quantity;
        BasketComponent.currentInstance.needService.updateNeed(need).subscribe();
      }
    }
  }
  /**
   * Creates an empty basket
   */
  clearBasket() {
    const clonedNeeds: Need[]  = [...BasketComponent.needs];

    clonedNeeds.forEach((need) => {
      this.removeFromBasket(need);
    });
  }
  /**
   * Get all needs that are in the basket
   */
  getBasket(): Need[] {
    return BasketComponent.needs;
  }

  /**
   * Removes a need from the basket
   *@param need the need that is being removed
   */
  removeFromBasket(need: Need): void {
    BasketComponent.needs = BasketComponent.needs.filter(element => {
      if(element === need) {
        // Remove user name from need's user basket list
        const currentUsername = localStorage.getItem("user");
        if(currentUsername != undefined) {
          delete need.userBaskets[currentUsername];
        }

        // Update the need
        BasketComponent.currentInstance.needService.updateNeed(need).subscribe();
        return false;
      }
      return true;
    });
    console.log(BasketComponent.needs);
  }

  basketTotal(): number {
    var total: number = 0;
    BasketComponent.needs.forEach(need => {
      total += need.price * this.getNeedQuantity(need);
    });
    return total;
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
      this.checkoutNeed(need, need.quantity, this.getNeedQuantity(need));
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

  getNeedQuantity(need: Need): number {
    const currentUserName = localStorage.getItem("user");
    if(currentUserName != null) {
      return need.userBaskets[currentUserName];
    }
    return 0;
  }

  protected readonly AccessControlService = AccessControlService;
}
