import { Component, OnInit } from '@angular/core';
import {RestapiService} from "../service/restapi.service";
import {LocalStorageService} from "../service/local-storage.service";
import {Router} from "@angular/router";
import {GlobalVariable} from "../global";
import { JSEncrypt } from 'jsencrypt';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public currentUser:any;
  username:string;
  password:string;

  encryptor: any;

  constructor(
    private apiService: RestapiService,
    private localStorageService: LocalStorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.encryptor = new JSEncrypt();

    const token = this.localStorageService.get("token");
    if (token != null) {
      this.navigateHome();
    }
  }

  doLogin() {

    const text = (this.username + ":" + this.password).trim();
    this.encryptor.setPublicKey(GlobalVariable.PUBLIC_KEY);
    let encryptedAuth = this.encryptor.encrypt(text);

    let response = this.apiService.login(encryptedAuth)
      .subscribe(
        (token: string) => {
          this.storeToken(token);
          this.navigateHome();
        }, error => {
          console.log(error);
        });
  }

  storeToken(token: string) {
    this.localStorageService.set("token", token);
  }

  navigateHome() {
    this.router.navigate(["/home"]);
  }

}
