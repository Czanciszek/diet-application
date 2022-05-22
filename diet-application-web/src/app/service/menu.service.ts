import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {Menu} from '../model/menu';
import {RestapiService} from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(
    private restApiService: RestapiService
  ) { }

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    weekCount: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(8)]),
    foodTypes: new FormControl(null, [Validators.required]),
    patientId: new FormControl(null),
    startDate: new FormControl(null, [Validators.required]),
    energyLimit: new FormControl(null, [Validators.required]),
    proteinsLimit: new FormControl(null, [Validators.required]),
    fatsLimit: new FormControl(null, [Validators.required]),
    carbohydratesLimit: new FormControl(null, [Validators.required])
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
    return this.restApiService.get<Menu[]>("menus/patient/" + patientId);
  }

  getMenuById(menuId): Observable<Menu[]> {
    return this.restApiService.get<Menu[]>("menus/" + menuId);
  }

  insertMenu(menu) {
    return this.restApiService.post(menu, "menus");
  }

  copyMenu(menu) {
    return this.restApiService.post(menu, "menus/copy");
  }

  updateMenu(menu) {
    return this.restApiService.put(menu, "menus/" + menu.id);
  }

  deleteMenu(id: string) {
    return this.restApiService.delete("menus/" + id);
  }
}
