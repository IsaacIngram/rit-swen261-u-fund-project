import { Need } from '../Need';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {

  static needs: Need[] = [];

  static addToBasket(need: Need): void {
    BasketComponent.needs.push(need);
  }

  clearBasket() {
    BasketComponent.needs = [];
  }

  getBasket(): Need[] {
    return BasketComponent.needs;
  }

  adjustQuantity(need: Need, delta: number): void {
    need.quantity+=delta;

    if(need.quantity < 1) {
      need.quantity = 1;
    }
  }

  removeFromBasket(need: Need): void {
    BasketComponent.needs = BasketComponent.needs.filter(n => n !== need);
  }
}