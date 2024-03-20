import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {CupboardComponent} from "./cupboard/cupboard.component";
import {BasketComponent} from "./basket/basket.component";
import { LoginComponent } from './login/login.component';
import {AboutComponent} from "./about/about.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '',  redirectTo: '/about', pathMatch: 'full' },
  { path: 'about', component: AboutComponent},
  { path: 'cupboard', component: CupboardComponent },
  { path: 'basket', component: BasketComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
