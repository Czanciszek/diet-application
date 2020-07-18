import { Component, OnInit } from '@angular/core';
import {RestapiService} from "../service/restapi.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  users: any;
  constructor(
    private service:RestapiService
  ) { }

  ngOnInit(): void {
  }

  getUsers() {
    let response = this.service.getUsers();
    response.subscribe(data => {
      this.users = data;
    });
  }
}
