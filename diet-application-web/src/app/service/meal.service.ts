import { Injectable } from '@angular/core';
import { UntypedFormArray, UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { RestapiService } from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class MealService {

  constructor(
    private restApiService: RestapiService
  ) { }

  form: UntypedFormGroup = new UntypedFormGroup({
    attachToRecipes: new UntypedFormControl(null),
    dishPortions: new UntypedFormControl(null),
    foodType: new UntypedFormControl(''),
    grams: new UntypedFormControl(null),
    id: new UntypedFormControl(null),
    isProduct: new UntypedFormControl(null),
    name: new UntypedFormControl('', [Validators.required]),
    originDishId: new UntypedFormControl(null),
    portions: new UntypedFormControl(null),
    productList: new UntypedFormArray([]),
    recipe: new UntypedFormControl(null)
  });

  addProductFormGroup(): UntypedFormGroup {
    return new UntypedFormGroup({
      productId: new UntypedFormControl(null),
      productName: new UntypedFormControl(null, [Validators.required]),
      grams: new UntypedFormControl(null, [Validators.required]),
      amount: new UntypedFormControl(null),
      amountType: new UntypedFormControl(null),
      amountTypes: new UntypedFormArray([])
    });
  }

  addAmountTypeFormGroup(): UntypedFormGroup {
    return new UntypedFormGroup({
      amountType: new UntypedFormControl(null),
      grams: new UntypedFormControl(null),
    })
  }

  initializeFormGroup() {
    this.clearForm();

    this.form.setValue({
      attachToRecipes: false,
      dishPortions: 1,
      foodType: '',
      grams: 0,
      id: null,
      isProduct: 0,
      name: '',
      originDishId: null,
      portions: 1,
      productList: [],
      recipe: ''
    })
  }

  populateForm(meal) {
    this.clearForm();
    
    if (meal.productList == null) meal.productList = [];
    let productIndex = 0;
    for (const product of meal.productList) {
      let products = <UntypedFormArray>this.form.get('productList');
      products.push(this.addProductFormGroup());
      this.setupProductAmountTypes(product.amountTypes, productIndex);
      productIndex += 1;
    }

    this.form.setValue(meal);
  }

  setupProductAmountTypes(amountTypes, productIndex: number) {
    let formProduct = this.form.get('productList').get([productIndex]);
    (<UntypedFormArray>formProduct.get('amountTypes')).clear();
    if (amountTypes == null) return;
    for (const amountType of amountTypes) {
      let formAmountTypes = <UntypedFormArray>formProduct.get('amountTypes');
      formAmountTypes.push(this.addAmountTypeFormGroup());
    }
  }

  clearForm() {
    (<UntypedFormArray>this.form.get('productList')).clear();
    this.form.reset();
  }

  insertMeal(weekMenuId: string, date: string) {
    return this.restApiService.post({ "weekMenuId": weekMenuId, "date": date, "meal": this.form.value }, "meals", "v2");
  }

  copyDay(menuId: string, newDate: string, originDate: string) {
    return this.restApiService.post({ "menuId": menuId, "newDate": newDate, "originDate": originDate }, "meals/copyDay", "v2");
  }

  copyMeal(menuId: string, newDate: string, mealId: string) {
    return this.restApiService.post({ "menuId": menuId, "newDate": newDate, "originMealId": mealId }, "meals/copyMeal", "v2");
  }

  updateMeal(weekMenuId: string) {
    return this.restApiService.put({ "weekMenuId": weekMenuId, "meal": this.form.value }, "meals", "v2");
  }

  removeMeal(weekMenuId: string, mealId: string) {
    return this.restApiService.put({ "weekMenuId": weekMenuId, "mealId": mealId }, "meals/removeMeal", "v2");
  }

  clearDay(weekMenuId: string, date: string) {
    return this.restApiService.put({ "weekMenuId": weekMenuId, "date": date }, "meals/clearDay", "v2");
  }
}
