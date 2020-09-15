import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  patientList: any;

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    header: new FormControl(null),
    primaryImageId: new FormControl(null),
    type: new FormControl(''),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    birthDate: new FormControl(''),
    numberPhone: new FormControl(''),
    email: new FormControl(''),
    bodyWeight: new FormControl(null),
    bodyHeight: new FormControl(null),
    currentLifestyleNote: new FormControl(''),
    changedLifestyleNote: new FormControl(''),
    dietaryPurpose: new FormControl(''),
    //measurements:...
    //interviewanswers
    //diets
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      header: null,
      primaryImageId: null,
      type: '',
      name: '',
      birthDate: '',
      numberPhone: '',
      email: '',
      bodyHeight: null,
      bodyWeight: null,
      currentLifestyleNote: '',
      changedLifestyleNote: '',
      dietaryPurpose: '',
    })
  }

  getPatients() {
    this.patientList = this.http.get("http://localhost:8080/api/v1/patients", this.httpOptions);
    return this.patientList;
  }

  populateForm(patient) {
    this.form.setValue(patient);
  }
}
