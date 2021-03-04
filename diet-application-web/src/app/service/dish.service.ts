import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class DishService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  dishList: any;

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    header: new FormControl(null),
    primaryImageId: new FormControl(null),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    type: new FormControl(''),
    products: new FormArray([]),
    mealType: new FormControl(null),
    portions: new FormControl(null),
    recipe: new FormControl('')
  });

  addProductFormGroup(): FormGroup {
    return new FormGroup( {
      grams: new FormControl(null),
      amount: new FormControl(null),
      amountType: new FormControl(null),
      foodProperties: new FormControl(),
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
      products: [
        {
          grams: null,
          amount: null,
          amountType: null,
          product: {
            id: null,
            name: null,
            primaryImageId: null,
            type: null
          },
          foodProperties: null,
        }
      ],
      mealType: '',
      portions: 1,
      recipe: ''
    })
  }

  populateForm(dish) {
    this.form.setValue(dish);
  }

  getDishes() {
    this.dishList = this.http.get("http://localhost:8080/api/v1/dishes", this.httpOptions);
    return this.dishList;
  }

  insertDish(dish) {
    return this.http.post("http://localhost:8080/api/v1/dishes", dish, this.httpOptions);
  }

  updateDish(dish) {
    return this.http.put("http://localhost:8080/api/v1/dishes/" + dish.id, dish, this.httpOptions);
  }

  deleteDish(id: string) {
    return this.http.delete("http://localhost:8080/api/v1/dishes/" + id, this.httpOptions).subscribe();
  }

}
