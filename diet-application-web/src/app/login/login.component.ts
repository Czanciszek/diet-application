import { Component, OnInit } from '@angular/core';
import { RestapiService } from "../service/restapi.service";
import { LocalStorageService } from "../service/local-storage.service";
import { NotificationService } from "../service/notification.service";
import { Router } from "@angular/router";
import { JSEncrypt } from 'jsencrypt';
import { LoginResult } from "../model/loginResult";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  PUBLIC_KEY = '305C300D06092A864886F70D0101010500034B003048024100B505F437415C1597674F37E6D6F60285ECB08EB27D17DA6F980E515EBC35BD821066F2006F0193A7A4BE8911DE339E02A0135EDAADBDC60A5174A80A2BC2B4490203010001'

  public currentUser: any;
  username: string;
  password: string;

  encryptor: any;

  constructor(
    private apiService: RestapiService,
    private localStorageService: LocalStorageService,
    private notificationService: NotificationService,
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
    this.encryptor.setPublicKey(this.PUBLIC_KEY);
    let encryptedAuth = this.encryptor.encrypt(text);

    let response = this.apiService.login(encryptedAuth)
      .subscribe(
        (loginResult: LoginResult) => {
          this.storeToken(loginResult.token);
          this.navigateHome();
        }, (error) => {
          if (error.status == 401) {
            this.notificationService.error(":: Niepoprawne dane logowania! ::");
          } else {
            this.notificationService.error(":: Wystąpiły problemy z serwerem - spróbuj ponownie później ::");
          }
        });
  }

  storeToken(token: string) {
    this.localStorageService.set("token", token);
  }

  navigateHome() {
    this.router.navigate(["/home"]);
  }

}
