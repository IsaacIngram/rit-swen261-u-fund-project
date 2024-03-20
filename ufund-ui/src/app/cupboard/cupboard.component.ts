import { Component } from '@angular/core';
import { AccessControlService } from "../access-control.service";
import {Need} from "../Need";
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
  needNames: String[];

  searchCharacter(Name: string) {
    this.getNeeds();
    this.needNames;
    for(let i = 0; i < this.getNeeds.length; i++){
      this.needNames[i] = this.getNeeds.name;
    }
    
  }

  add(name: string) {
    name = name.trim();
    if(!name){
      return;
      }
    }

}
