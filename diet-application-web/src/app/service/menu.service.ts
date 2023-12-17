import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Observable } from "rxjs";
import { Menu } from '../model/menu';
import { RestapiService } from "./restapi.service";
import { CalendarDateValidator } from "../utils/calendarDateValidator"

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor(
    private restApiService: RestapiService
  ) { }

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    weekMenusCount: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(8)]),
    foodTypes: new FormControl(null, [Validators.required]),
    patientId: new FormControl(null),
    startDate: new FormControl(null, [Validators.required, CalendarDateValidator]),
    recommendations: new FormControl(null),
    energyLimit: new FormControl(null, [Validators.required]),
    proteinsLimit: new FormControl(null, [Validators.required]),
    fatsLimit: new FormControl(null, [Validators.required]),
    carbohydratesLimit: new FormControl(null, [Validators.required])
  });

  initializeFormGroup() {
    this.form.reset();
    this.form.setValue({
      id: null,
      weekMenusCount: 1,
      foodTypes: null,
      patientId: null,
      startDate: null,
      recommendations: null,
      energyLimit: 0,
      proteinsLimit: 0,
      fatsLimit: 0,
      carbohydratesLimit: 0
    })
  }

  populateForm(menu) {
    this.form.reset();
    this.form.setValue(menu);
  }

  menuList: any;

  getMenusByPatientId(patientId): Observable<Menu[]> {
    return this.restApiService.get<Menu[]>("menus/patient/" + patientId, "v2");
  }

  getMenuById(menuId): Observable<Menu[]> {
    return this.restApiService.get<Menu[]>("menus/" + menuId, "v2");
  }

  getMenuProducts(menuId) {
    return this.restApiService.get("menus/" + menuId + "/products", "v2");
  }

  insertMenu(menu) {
    return this.restApiService.post(menu, "menus", "v2");
  }

  copyMenu(menu) {
    return this.restApiService.post(menu, "menus/copy", "v2");
  }

  replaceProductInMenu(productReplaceType) {
    return this.restApiService.post(productReplaceType, "menus/product-replace", "v2");
  }

  updateMenu(menu) {
    return this.restApiService.put(menu, "menus", "v2");
  }

  deleteMenu(id: string) {
    return this.restApiService.delete("menus/" + id, "v2");
  }
}
