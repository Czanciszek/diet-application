import { Component, OnInit } from '@angular/core';
import {RestapiService} from "../service/restapi.service";
import {GlobalVariable} from "../global";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(
    private service:RestapiService,
    private router:Router
  ) { }

  ngOnInit(): void {
  }

  navigateToProducts() {
    this.router.navigate(["/products"]);
  }

  navigateToDishes() {
    this.router.navigate(["/dishes"]);
  }
}
