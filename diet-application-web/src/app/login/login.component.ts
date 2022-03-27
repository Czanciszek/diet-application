import { Component, OnInit } from '@angular/core';
import {RestapiService} from "../service/restapi.service";
import {LocalStorageService} from "../service/local-storage.service";
import {Router} from "@angular/router";
import {GlobalVariable} from "../global";

import {Buffer} from 'buffer/';
import * as crypto from "crypto-browserify";

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
    private apiService: RestapiService,
    private localStorageService: LocalStorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const token = this.localStorageService.get("token");
    if (token != null) {
      this.navigateHome();
    }
  }

  doLogin() {

    let privateKey = "30820156020100300D06092A864886F70D0101010500048201403082013C020100024100B505F437415C1597674F37E6D6F60285ECB08EB27D17DA6F980E515EBC35BD821066F2006F0193A7A4BE8911DE339E02A0135EDAADBDC60A5174A80A2BC2B4490203010001024100A98EBC238D7AFD566594141E8AB6BFC245E352A5E8FA10C67A3CDF06C76FE6E278ED63B201046942EC31901176C73809ABC064257641E1731EF0AFC386124221022100F26E1A0F0D0AE27AB34BB66A8924995B83CE1BD1FFE3B3BE268B5BD417733425022100BF27EC89DD1C9FD025AF1FF13A1D8F967D05A1A0409785C514A25B7FB1D9945502210093220D80BAFAB32FA2E7BDA374EE9880B942FB117DCB55887F5E2EF24EF5255D022073F3F9CDDEEC601CD8DF141735D37CA8228A69F2557C144EEA275A2D696ABC49022100B642CF9AA424B59C9E2B2DD2898DD8666ED5487047949A743898E3833CC56F9F";

    let buffer = new Buffer("aaa:aaa");
    let encrypted = crypto.privateEncrypt(privateKey, buffer);

    let encrytped = encrypted.toString('base64');

    console.log("encrypted");

    let response = this.apiService.login(this.username, this.password)
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
