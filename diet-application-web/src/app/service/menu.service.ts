import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import { FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {Menu} from '../model/menu';

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

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    weekCount: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(8)]),
    foodTypes: new FormControl(null),
    patientId: new FormControl(null),
    startDate: new FormControl(null, [Validators.required]),
    energyLimit: new FormControl(null, [Validators.required]),
    proteinsLimit: new FormControl(null),
    fatsLimit: new FormControl(null),
    carbohydratesLimit: new FormControl(null)
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      weekCount: 1,
      foodTypes: null,
      patientId: null,
      startDate: null,
      energyLimit: 0,
      proteinsLimit: 0,
      fatsLimit: 0,
      carbohydratesLimit: 0
    })
  }

  populateForm(menu) {
    this.form.setValue(menu);
  }

  menuList: any;

  getMenusByPatientId(patientId): Observable<Menu[]> {
    return this.http.get<Menu[]>(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "menus/patient/" + patientId, this.httpOptions)
      .pipe();
  }

  getMenuById(menuId): Observable<Menu[]> {
    return this.http.get<Menu[]>(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "menus/" + menuId, this.httpOptions)
      .pipe();
  }

  insertMenu(menu) {
    return this.http.post(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "menus", menu, this.httpOptions);
  }

  updateMenu(menu) {
    return this.http.put(GlobalVariable.SERVER_ADDRESS +
          GlobalVariable.DATABASE_SERVICE + "menus/" + menu.id, menu, this.httpOptions);
  }

  copyMenu(menu) {
    return this.http.post(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "menus/copy", menu, this.httpOptions);
  }

  deleteMenu(id: string) {
      return this.http.delete(GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + "menus/" + id, this.httpOptions);
  }
}
