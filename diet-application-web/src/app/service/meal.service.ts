import { Injectable } from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";

@Injectable({
  providedIn: 'root'
})
export class MealService {

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
    name: new FormControl(''),
    dayMealId: new FormControl('', [Validators.required]),
    productList: new FormArray([]),
    isProduct: new FormControl(null),
    portions: new FormControl(null),
    recipe: new FormControl(null),
    foodType: new FormControl('')
  });

  addProductFormGroup(): FormGroup {
    return new FormGroup( {
      productId: new FormControl(null),
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
      isProduct: 0,
      portions: 1,
      recipe: '',
      foodType: ''
    })
  }

  populateForm(meal) {
    this.form.setValue(meal);
  }

  getMealListByListId(dayMealIdList) {
    return this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "meals/list/" + dayMealIdList, this.httpOptions);
  }

  insertMeal(meal) {
    return this.http.post(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +"meals", meal, this.httpOptions);
  }

  copyMeal(meal) {
    return this.http.post(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "meals/copy", meal, this.httpOptions);
  }

  updateMeal(meal) {
    return this.http.put(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "meals/" + meal.id, meal, this.httpOptions);
  }

  deleteMeal(id: string) {
    return this.http.delete(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "meals/" + id, this.httpOptions);
  }
}
