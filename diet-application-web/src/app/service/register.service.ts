import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators, ValidatorFn, FormArray } from "@angular/forms";
import { RestapiService } from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(
    private restApiService: RestapiService
  ) { }

  form: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    password: new FormControl('', [Validators.required, Validators.minLength(3)]),
    repeatPassword: new FormControl('', [Validators.required, this.passwordMatch()]),
    userType: new FormControl(null),
    email: new FormControl(null, [Validators.email, Validators.required]),
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
    return (controlArray: FormArray) => {
      if (this.form == null) return null;
      let pass = this.form.get('password').value;
      let confirmPass = this.form.get('repeatPassword').value;
      return (pass != confirmPass) ? { passwordMismatch: { value: true } } : null;
    };
  }
}


