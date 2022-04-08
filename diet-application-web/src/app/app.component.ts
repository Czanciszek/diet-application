import {Component, OnInit} from '@angular/core';
import {GlobalVariable} from "./global";
import {LocalStorageService} from "./service/local-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  currentUserName = GlobalVariable.CURRENT_USER_LOGIN;

  constructor(
    private router: Router,
    private localStorageService: LocalStorageService,
  ) { }

  ngOnInit(): void {

  }

  w3_open() {
    document.getElementById("mySidebar").style.display = "block";
  }

  w3_close() {
    document.getElementById("mySidebar").style.display = "none";
  }

  logout() {
    this.w3_close();
    this.localStorageService.remove("token");
    this.router.navigate(["/"]);
  }
}

