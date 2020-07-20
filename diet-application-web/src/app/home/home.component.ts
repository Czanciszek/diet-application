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

  currentUserName = GlobalVariable.CURRENT_USER_LOGIN;
  users: any;

  constructor(
    private service:RestapiService,
    private router:Router
  ) { }

  ngOnInit(): void {
  }

  navigateToProducts() {
    this.router.navigate(["/products"]);
  }

  w3_open() {
    document.getElementById("mySidebar").style.display = "block";
  }

  w3_close() {
    document.getElementById("mySidebar").style.display = "none";
  }
}
