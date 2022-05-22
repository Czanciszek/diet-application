import { Component, OnInit } from '@angular/core';
import {NotificationService} from "../service/notification.service";
import {RestapiService} from "../service/restapi.service";
import {RegisterService} from "../service/register.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    constructor(
      private notificationService: NotificationService,
      private registerService: RegisterService,
      private restapiService: RestapiService
    ) { }

    userForm = this.registerService.form;

    ngOnInit(): void {

    }

    onSubmit() {
      if (!this.registerService.form.valid) {
        this.notificationService.error(":: Wprowadzone dane są niepoprawne ::");
        return;
      }

      this.registerService.registerUser().subscribe();
    }

}
