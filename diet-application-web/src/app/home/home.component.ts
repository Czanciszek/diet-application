import { Component } from '@angular/core';
import { Router } from "@angular/router";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css'],
    standalone: false
})
export class HomeComponent {

  constructor(
    private router: Router
  ) { }

  navigateToProducts() {
    this.router.navigate(["/products"]);
  }

  navigateToDishes() {
    this.router.navigate(["/dishes"]);
  }

  navigateToPatients() {
    this.router.navigate(["/patients"]);
  }
}
