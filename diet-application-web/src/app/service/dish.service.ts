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
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    products: new FormArray([]),
    foodType: new FormControl(null),
    portions: new FormControl(null),
    recipe: new FormControl(''),
    menuId: new FormControl(null)
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
      foodType: '',
      portions: 1,
      recipe: '',
      menuId: null,
    })
  }

  populateForm(dish) {
    this.form.setValue(dish);
  }

  populateFormFromMeal(meal) {
    this.form.get('name').patchValue(meal.name);
    this.form.get('foodType').patchValue(meal.foodType);
    this.form.get('portions').patchValue(meal.portions);
    this.form.get('recipe').patchValue(meal.recipe);

    (<FormArray>this.form.get('products')).clear();
    for (let product of meal.productList) {
      let productForm = this.addProductFormGroup();
      productForm.get('productId').patchValue(product.productId);
      productForm.get('grams').patchValue(product.grams);
      productForm.get('amount').patchValue(product.amount);
      productForm.get('amountType').patchValue(product.amountType);
      (<FormArray>this.form.get('products')).push(productForm);
    }
  }

  getDishes() {
    this.dishList = this.http.get(GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + "dishes", this.httpOptions);
    return this.dishList;
  }

  insertDish(dish) {
    return this.http.post(GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + "dishes", dish, this.httpOptions);
  }

  updateDish(dish) {
    return this.http.put(GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + "dishes/" + dish.id, dish, this.httpOptions);
  }

  deleteDish(id: string) {
    return this.http.delete(GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + "dishes/" + id, this.httpOptions);
  }

}
