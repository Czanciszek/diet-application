import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators, ValidatorFn } from "@angular/forms";
import { RestapiService } from "./restapi.service";

import { Dish } from '../model/dish';

@Injectable({
  providedIn: 'root'
})
export class DishService {

  constructor(
    private restApiService: RestapiService
  ) { }

  dishList: Dish[];

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    name: new FormControl('', [Validators.required, Validators.minLength(3), this.dishAlreadyExist()]),
    products: new FormArray([]),
    foodType: new FormControl(null, [Validators.required]),
    portions: new FormControl(null),
    recipe: new FormControl('')
  });

  addProductFormGroup(): FormGroup {
    return new FormGroup({
      grams: new FormControl(null, [Validators.required]),
      amount: new FormControl(null),
      amountType: new FormControl(null),
      amountTypes: new FormArray([]),
      productId: new FormControl(null, [Validators.required]),
      productName: new FormControl(null)
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
      products: [],
      foodType: '',
      portions: 1,
      recipe: '',
    })
  }

  populateForm(dish) {
    this.clearForm();
    if (dish.products == null) dish.products = [];
    let productIndex = 0;
    for (const product of dish.products) {
      let products = <FormArray>this.form.get('products');
      products.push(this.addProductFormGroup());

      this.setupProductAmountTypes(product.amountTypes, productIndex);
      productIndex += 1;
    }

    this.form.setValue(dish);
  }

  setupProductAmountTypes(amountTypes, productIndex: number) {
    let formProduct = this.form.get('products').get([productIndex]);
    (<FormArray>formProduct.get('amountTypes')).clear();
    if (amountTypes == null) return;
    for (const amountType of amountTypes) {
      let formAmountTypes = <FormArray>formProduct.get('amountTypes');
      formAmountTypes.push(this.addAmountTypeFormGroup());
    }
  }

  clearForm() {
    (<FormArray>this.form.get('products')).clear();
    this.form.reset();
  }

  dishAlreadyExist(): ValidatorFn {
    return (controlArray: FormArray) => {
      if (this.dishList == null) return null;

      let dishId = this.form.get('id').value;
      if (dishId) return null;

      let dishName = this.form.get('name').value;
      if (!dishName) return null;

      let dishNames = this.dishList.map(dish => { return dish.name.trim().toLowerCase(); });
      return dishNames.includes(dishName.trim().toLowerCase()) ? { dishAlreadyExist: { value: true } } : null;
    };
  }

  getDishes() {
    return this.restApiService.get("dishes");
  }

  getDishUsages(patientId) {
    return this.restApiService.get("dishes/patient-usage/" + patientId);
  }

  insertDish(dish) {
    return this.restApiService.post(dish, "dishes");
  }

  updateDish(dish) {
    return this.restApiService.put(dish, "dishes");
  }

  deleteDish(id: string) {
    return this.restApiService.delete("dishes/" + id);
  }

}
