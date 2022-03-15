import { Injectable } from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {RestapiService} from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class MealService {

  constructor(
    private restApiService: RestapiService
  ) { }

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    name: new FormControl(''),
    dayMealId: new FormControl('', [Validators.required]),
    originMealId: new FormControl(null),
    productList: new FormArray([]),
    isProduct: new FormControl(null),
    portions: new FormControl(null),
    grams: new FormControl(null),
    dishPortions: new FormControl(null),
    recipe: new FormControl(null),
    foodType: new FormControl('')
  });

  addProductFormGroup(): FormGroup {
    return new FormGroup( {
      productId: new FormControl(null),
      productName: new FormControl(null),
      grams: new FormControl(null),
      amount: new FormControl(null),
      amountType: new FormControl(null)
    });
  }

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      name: '',
      productList: [],
      dayMealId: '',
      originMealId: null,
      isProduct: 0,
      portions: 1,
      grams: 0,
      dishPortions: 1,
      recipe: '',
      foodType: ''
    })
  }

  populateForm(meal) {
    this.form.setValue(meal);
  }

  getMealListByWeekMealId(weekMealId) {
    return this.restApiService.get("meals/list/" + weekMealId);
  }

  insertMeal(meal) {
    return this.restApiService.post("meals", meal);
  }

  copyMeal(meal) {
    return this.restApiService.post("meals/copy", meal);
  }

  updateMeal(meal) {
    return this.restApiService.put("meals/" + meal.id, meal);
  }

  deleteMeal(id: string) {
    return this.restApiService.delete( "meals/" + id);
  }
}
