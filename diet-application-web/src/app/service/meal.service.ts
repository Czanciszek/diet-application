import { Injectable } from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
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
    header: new FormControl(null),
    primaryImageId: new FormControl(null),
    name: new FormControl(''),
    type: new FormControl(''),
    dayMealId: new FormControl('', [Validators.required]),
    grams: new FormControl(null),
    productForDishList: new FormArray([]),
    isProduct: new FormControl(null),
    portions: new FormControl(null),
    recipe: new FormControl(null),
    mealType: new FormControl('')
  });

  addProductFormGroup(): FormGroup {
    return new FormGroup( {
      grams: new FormControl(null),
      amount: new FormControl(null),
      amountType: new FormControl(null),
      product: new FormGroup( {
        id: new FormControl(null),
        name: new FormControl(null),
        primaryImageId: new FormControl(null),
        type: new FormControl(null)
      }),
    });
  }

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      header: null,
      primaryImageId: null,
      name: '',
      type: '',
      productForDishList: [],
      dayMealId: '',
      grams: null,
      isProduct: 0,
      portions: 1,
      recipe: '',
      mealType: ''
    })
  }

  populateForm(meal) {
    this.form.setValue(meal);
  }

  getMealListByListId(dayMealIdList) {
    return this.http.get("http://localhost:8080/api/v1/meals/list/" + dayMealIdList, this.httpOptions);
  }

  insertMeal(meal) {
    return this.http.post("http://localhost:8080/api/v1/meals", meal, this.httpOptions);
  }

  copyMeal(meal) {
    return this.http.post("http://localhost:8080/api/v1/meals/copy", meal, this.httpOptions);
  }

  updateMeal(meal) {
    return this.http.put("http://localhost:8080/api/v1/meals/" + meal.id, meal, this.httpOptions);
  }

  deleteMeal(id: string) {
    return this.http.delete("http://localhost:8080/api/v1/meals/" + id, this.httpOptions);
  }
}
