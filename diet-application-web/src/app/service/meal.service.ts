import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from "@angular/forms";
import { RestapiService } from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class MealService {

  constructor(
    private restApiService: RestapiService
  ) { }

  form: FormGroup = new FormGroup({
    attachToRecipes: new FormControl(null),
    dishPortions: new FormControl(null),
    foodType: new FormControl(''),
    grams: new FormControl(null),
    id: new FormControl(null),
    isProduct: new FormControl(null),
    name: new FormControl('', [Validators.required]),
    originDishId: new FormControl(null),
    portions: new FormControl(null),
    productList: new FormArray([]),
    recipe: new FormControl(null)
  });

  addProductFormGroup(): FormGroup {
    return new FormGroup({
      productId: new FormControl(null),
      productName: new FormControl(null, [Validators.required]),
      grams: new FormControl(null, [Validators.required]),
      amount: new FormControl(null),
      amountType: new FormControl(null),
      amountTypes: new FormArray([])
    });
  }

  addAmountTypeFormGroup(): FormGroup {
    return new FormGroup({
      amountType: new FormControl(null),
      grams: new FormControl(null),
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
      let products = <FormArray>this.form.get('productList');
      products.push(this.addProductFormGroup());
      this.setupProductAmountTypes(product.amountTypes, productIndex);
      productIndex += 1;
    }

    this.form.setValue(meal);
  }

  setupProductAmountTypes(amountTypes, productIndex: number) {
    let formProduct = this.form.get('productList').get([productIndex]);
    (<FormArray>formProduct.get('amountTypes')).clear();
    if (amountTypes == null) return;
    for (const amountType of amountTypes) {
      let formAmountTypes = <FormArray>formProduct.get('amountTypes');
      formAmountTypes.push(this.addAmountTypeFormGroup());
    }
  }

  clearForm() {
    (<FormArray>this.form.get('productList')).clear();
    this.form.reset();
  }

  insertMeal(weekMenuId: string, date: string) {
    return this.restApiService.post({ "weekMenuId": weekMenuId, "date": date, "meal": this.form.value }, "meals", "v2");
  }

  copyDay(weekMenuId: string, newDate: string, originDate: string) {
    return this.restApiService.post({ "weekMenuId": weekMenuId, "newDate": newDate, "originDate": originDate }, "meals/copyDay", "v2");
  }

  copyMeal(weekMenuId: string, newDate: string, mealId: string) {
    return this.restApiService.post({ "weekMenuId": weekMenuId, "newDate": newDate, "originMealId": mealId }, "meals/copyMeal", "v2");
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
