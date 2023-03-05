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
    id: new FormControl(null),
    name: new FormControl('', [Validators.required]),
    dayMealId: new FormControl(''),
    originMealId: new FormControl(null),
    baseDishId: new FormControl(null),
    productList: new FormArray([]),
    isProduct: new FormControl(null),
    portions: new FormControl(null),
    grams: new FormControl(null),
    dishPortions: new FormControl(null),
    recipe: new FormControl(null),
    foodType: new FormControl('')
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
      id: null,
      name: '',
      productList: [],
      dayMealId: '',
      originMealId: null,
      baseDishId: null,
      isProduct: 0,
      portions: 1,
      grams: 0,
      dishPortions: 1,
      recipe: '',
      foodType: ''
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

  getMealListByWeekMealId(weekMealId) {
    return this.restApiService.get("meals/list/" + weekMealId);
  }

  insertMeal(meal) {
    return this.restApiService.post(meal, "meals");
  }

  copyMeal(meal) {
    return this.restApiService.post(meal, "meals/copy");
  }

  updateMeal(meal) {
    return this.restApiService.put(meal, "meals/" + meal.id);
  }

  deleteMeal(id: string) {
    return this.restApiService.delete("meals/" + id);
  }
}
