import {Injectable} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators, ValidatorFn} from "@angular/forms";
import {RestapiService} from "./restapi.service";

import {Dish} from '../model/dish';

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
    return new FormGroup( {
      grams: new FormControl(null, [Validators.required]),
      amount: new FormControl(null),
      amountType: new FormControl(null),
      productId: new FormControl(null, [Validators.required]),
      productName: new FormControl(null)
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
          productName: null,
        }
      ],
      foodType: '',
      portions: 1,
      recipe: '',
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

  dishAlreadyExist(): ValidatorFn {
    return (controlArray: FormArray) => {
    if (this.dishList == null) return null;

    let dishId = this.form.get('id').value;
    if (dishId) return null;

    let dishName = this.form.get('name').value;
    if (!dishName) return null;

    let dishNames = this.dishList.map( dish => { return dish.name.trim().toLowerCase(); });
    return dishNames.includes(dishName.trim().toLowerCase()) ? { dishAlreadyExist: { value: true } } : null;
    };
  }

  getDishes() {
    return this.restApiService.get("dishes");
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
