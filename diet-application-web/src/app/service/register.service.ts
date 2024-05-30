import { Injectable } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators, ValidatorFn, UntypedFormArray } from "@angular/forms";
import { RestapiService } from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(
    private restApiService: RestapiService
  ) { }

  form: UntypedFormGroup = new UntypedFormGroup({
    name: new UntypedFormControl('', [Validators.required, Validators.minLength(3)]),
    password: new UntypedFormControl('', [Validators.required, Validators.minLength(3)]),
    repeatPassword: new UntypedFormControl('', [Validators.required, this.passwordMatch()]),
    userType: new UntypedFormControl(null),
    email: new UntypedFormControl(null, [Validators.email, Validators.required]),
  });

  initializeFormGroup() {
    this.form.setValue({
      name: '',
      password: '',
      repeatPassword: '',
      userType: '',
      email: ''
    })
  }

  registerUser() {
    return this.restApiService.register(this.form.value);
  }

  passwordMatch(): ValidatorFn {
    return (controlArray: UntypedFormArray) => {
      if (this.form == null) return null;
      let pass = this.form.get('password').value;
      let confirmPass = this.form.get('repeatPassword').value;
      return (pass != confirmPass) ? { passwordMismatch: { value: true } } : null;
    };
  }
}


