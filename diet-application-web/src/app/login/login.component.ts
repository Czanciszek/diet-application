import { Component, OnInit } from '@angular/core';
import {RestapiService} from "../service/restapi.service";
import {Router} from "@angular/router";
import {GlobalVariable} from "../global";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public currentUser:any;
  username:string;
  password:string;
  message:any;

  constructor(
    private service:RestapiService,
    private router:Router
  ) { }

  ngOnInit(): void {
  }

  doLogin() {
    let response = this.service.login(this.username, this.password);
    response.subscribe( () => {
      GlobalVariable.CURRENT_USER_LOGIN = this.username;
      GlobalVariable.CURRENT_USER_PASSWORD = this.password;
      this.router.navigate(["/home"]);
    });
  }

}
