import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class DishService {

  serverAddress = "http://localhost:8080/";
  dbService = "api/psql/";

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
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
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
      productId: new FormControl(null)
    });
  }

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      name: '',
      products: [
        {
          grams: null,
          amount: null,
          amountType: null,
          productId: null,
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
    this.dishList = this.http.get(this.serverAddress + this.dbService + "dishes", this.httpOptions);
    return this.dishList;
  }

  insertDish(dish) {
    return this.http.post(this.serverAddress + this.dbService + "dishes", dish, this.httpOptions);
  }

  updateDish(dish) {
    return this.http.put(this.serverAddress + this.dbService + "dishes/" + dish.id, dish, this.httpOptions);
  }

  deleteDish(id: string) {
    return this.http.delete(this.serverAddress + this.dbService + "dishes/" + id, this.httpOptions).subscribe();
  }

}
