import {Component, OnInit} from '@angular/core';
import {GlobalVariable} from "./global";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  currentUserName = GlobalVariable.CURRENT_USER_LOGIN;
  loginPage = false;

  constructor(
  ) { }

  ngOnInit(): void {
    if (window.location.href == "http://localhost:4200/login") {
      this.loginPage = true;
    }
  }

  w3_open() {
    document.getElementById("mySidebar").style.display = "block";
  }

  w3_close() {
    document.getElementById("mySidebar").style.display = "none";
  }
}

