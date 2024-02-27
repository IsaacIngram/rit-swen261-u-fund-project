import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CupboardComponent} from "./cupboard/cupboard.component";

const routes: Routes = [
  { path: '',  redirectTo: '/about', pathMatch: 'full' },
  { path: 'cupboard', component: CupboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
