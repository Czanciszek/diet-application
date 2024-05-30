import { Injectable } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
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

  form: UntypedFormGroup = new UntypedFormGroup({
    id: new UntypedFormControl(null),
    weekMenusCount: new UntypedFormControl(null, [Validators.required, Validators.min(1), Validators.max(8)]),
    foodTypes: new UntypedFormControl(null, [Validators.required]),
    patientId: new UntypedFormControl(null),
    startDate: new UntypedFormControl(null, [Validators.required, CalendarDateValidator]),
    recommendations: new UntypedFormControl(null),
    energyLimit: new UntypedFormControl(null, [Validators.required]),
    proteinsLimit: new UntypedFormControl(null, [Validators.required]),
    fatsLimit: new UntypedFormControl(null, [Validators.required]),
    carbohydratesLimit: new UntypedFormControl(null, [Validators.required])
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
