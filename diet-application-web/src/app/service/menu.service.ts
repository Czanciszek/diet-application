import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  patientId: '';
  menuList: any;

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    header: new FormControl(null),
    primaryImageId: new FormControl(null),
    type: new FormControl(''),
    name: new FormControl(''),
    patientDocRef: new FormGroup({
      id: new FormControl(null),
      name: new FormControl(null),
      primaryImageId: new FormControl(null),
      type: new FormControl(null)
    }),
    measurementDocRef: new FormGroup({
      id: new FormControl(null),
      name: new FormControl(null),
      primaryImageId: new FormControl(null),
      type: new FormControl(null)
    }),
    weekMealList: new FormArray([]),
    mealTypes: new FormArray([]),
    startDate: new FormControl(''),
    endDate: new FormControl(''),
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      header: null,
      primaryImageId: null,
      type: '',
      name: '',
      patientDocRef: {
        id: null,
        name: null,
        primaryImageId: null,
        type: null
      },
      measurementDocRef: {
        id: null,
        name: null,
        primaryImageId: null,
        type: null
      },
      weekMealList: [],
      mealTypes: [],
      startDate: '',
      endDate: ''
    })
  }

  getMenusByPatientId(patientId) {
    this.menuList = this.http.get("http://localhost:8080/api/v1/menus/patient/" + patientId, this.httpOptions);
    return this.menuList;
  }
}
