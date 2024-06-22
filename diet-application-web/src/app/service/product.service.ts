import { Injectable } from '@angular/core';
import { Validators, FormControl, FormGroup, FormArray } from "@angular/forms";

import { RestapiService } from "./restapi.service";
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(
    private restApiService: RestapiService
  ) { }

  productList: Product[];

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    category: new FormGroup({
      id: new FormControl(null, Validators.required),
      category: new FormControl(null, Validators.required),
      subcategory: new FormControl(null, Validators.required),
    }),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    foodProperties: new FormGroup({
      id: new FormControl(null),
      energyValue: new FormControl(null),
      proteins: new FormControl(null),
      fats: new FormControl(null),
      saturatedFattyAcids: new FormControl(null),
      monoUnsaturatedFattyAcids: new FormControl(null),
      polyUnsaturatedFattyAcids: new FormControl(null),
      cholesterol: new FormControl(null),
      carbohydrates: new FormControl(null),
      sucrose: new FormControl(null),
      dietaryFibres: new FormControl(null),
      sodium: new FormControl(null),
      potassium: new FormControl(null),
      calcium: new FormControl(null),
      phosphorus: new FormControl(null),
      magnesium: new FormControl(null),
      iron: new FormControl(null),
      selenium: new FormControl(null),
      betaCarotene: new FormControl(null),
      vitaminD: new FormControl(null),
      vitaminC: new FormControl(null),
    }),
    amountTypes: new FormArray([]),
    allergenTypes: new FormControl([])
  });

  initializeFormGroup() {
    this.clearForm();
    (<FormArray>this.form.get('amountTypes')).push(this.addAmountTypeFormGroup());

    this.form.setValue({
      id: null,
      category: {
        id: '',
        category: '',
        subcategory: '',
      },
      name: '',
      foodProperties: {
        id: null,
        energyValue: null,
        proteins: null,
        fats: null,
        saturatedFattyAcids: null,
        monoUnsaturatedFattyAcids: null,
        polyUnsaturatedFattyAcids: null,
        cholesterol: null,
        carbohydrates: null,
        sucrose: null,
        dietaryFibres: null,
        sodium: null,
        potassium: null,
        calcium: null,
        phosphorus: null,
        magnesium: null,
        iron: null,
        selenium: null,
        betaCarotene: null,
        vitaminD: null,
        vitaminC: null,
      },
      amountTypes: [
        {
          amountType: null,
          grams: null,
        }
      ],
      allergenTypes: [],
    })
  }

  addAmountTypeFormGroup(): FormGroup {
    return new FormGroup({
      amountType: new FormControl(null, Validators.required),
      grams: new FormControl(null, Validators.required),
    });
  }

  getProducts() {
    return this.restApiService.get("products", "v2");
  }

  getProductsByDishId(dishId) {
    return this.restApiService.get("products/dishlist/" + dishId, "v2");
  }

  getCategories() {
    return this.restApiService.get("products/categories", "v2");
  }

  insertProduct(product) {
    return this.restApiService.post(product, "products", "v2");
  }

  updateProduct(product) {
    return this.restApiService.put(product, "products", "v2");
  }

  deleteProduct(id: string) {
    return this.restApiService.delete("products/" + id, "v2");
  }

  populateForm(product) {
    this.clearForm();
    if (product.amountTypes == null) product.amountTypes = [];
    for (var i = 0; i < product.amountTypes.length; i++) {
      (<FormArray>this.form.get('amountTypes')).push(this.addAmountTypeFormGroup());
    }

    this.form.setValue(product);
  }

  clearForm() {
    (<FormArray>this.form.get('amountTypes')).clear();
    this.form.reset();
  }
}
