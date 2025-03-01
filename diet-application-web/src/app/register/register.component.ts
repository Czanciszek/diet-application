import { Component, OnInit } from '@angular/core';
import { NotificationService } from "../service/notification.service";
import { RestapiService } from "../service/restapi.service";
import { RegisterService } from "../service/register.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css'],
    standalone: false
})
export class RegisterComponent implements OnInit {

  constructor(
    private notificationService: NotificationService,
    private registerService: RegisterService
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
