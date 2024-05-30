import { Injectable } from '@angular/core';
import { UntypedFormArray, UntypedFormControl, UntypedFormGroup, Validators, ValidatorFn } from "@angular/forms";
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

  form: UntypedFormGroup = new UntypedFormGroup({
    id: new UntypedFormControl(null),
    name: new UntypedFormControl('', [Validators.required, Validators.minLength(3), this.dishAlreadyExist()]),
    products: new UntypedFormArray([]),
    foodType: new UntypedFormControl(null, [Validators.required]),
    portions: new UntypedFormControl(null),
    recipe: new UntypedFormControl('')
  });

  addProductFormGroup(): UntypedFormGroup {
    return new UntypedFormGroup({
      grams: new UntypedFormControl(null, [Validators.required]),
      amount: new UntypedFormControl(null),
      amountType: new UntypedFormControl(null),
      amountTypes: new UntypedFormArray([]),
      productId: new UntypedFormControl(null, [Validators.required]),
      productName: new UntypedFormControl(null)
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
      let products = <UntypedFormArray>this.form.get('products');
      products.push(this.addProductFormGroup());

      this.setupProductAmountTypes(product.amountTypes, productIndex);
      productIndex += 1;
    }

    this.form.setValue(dish);
  }

  setupProductAmountTypes(amountTypes, productIndex: number) {
    let formProduct = this.form.get('products').get([productIndex]);
    (<UntypedFormArray>formProduct.get('amountTypes')).clear();
    if (amountTypes == null) return;
    for (const amountType of amountTypes) {
      let formAmountTypes = <UntypedFormArray>formProduct.get('amountTypes');
      formAmountTypes.push(this.addAmountTypeFormGroup());
    }
  }

  clearForm() {
    (<UntypedFormArray>this.form.get('products')).clear();
    this.form.reset();
  }

  dishAlreadyExist(): ValidatorFn {
    return (controlArray: UntypedFormArray) => {
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
    return this.restApiService.get("dishes", "v2");
  }

  getDishUsages(patientId) {
    return this.restApiService.get("dishes/patient-usage/" + patientId, "v2");
  }

  insertDish(dish) {
    return this.restApiService.post(dish, "dishes", "v2");
  }

  updateDish(dish) {
    return this.restApiService.put(dish, "dishes", "v2");
  }

  deleteDish(id: string) {
    return this.restApiService.delete("dishes/" + id, "v2");
  }

}
