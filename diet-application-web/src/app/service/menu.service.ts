import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {Menu} from '../model/menu';
import {init} from "protractor/built/launcher";

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

  menuList: any;

  getMenusByPatientId(patientId): Observable<Menu[]> {
    return this.http.get<Menu[]>("http://localhost:8080/api/v1/menus/patient/" + patientId, this.httpOptions)
      .pipe();
  }

  getMenuById(menuId): Observable<Menu[]> {
    return this.http.get<Menu[]>("http://localhost:8080/api/v1/menus/" + menuId, this.httpOptions)
      .pipe();
  }
}
