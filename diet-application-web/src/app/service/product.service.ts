import { Injectable } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, UntypedFormArray, Validators } from "@angular/forms";

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

  form: UntypedFormGroup = new UntypedFormGroup({
    id: new UntypedFormControl(null),
    category: new UntypedFormGroup({
      category: new UntypedFormControl('', Validators.required),
      subcategory: new UntypedFormControl('', Validators.required),
    }),
    name: new UntypedFormControl('', [Validators.required, Validators.minLength(3)]),
    foodProperties: new UntypedFormGroup({
      id: new UntypedFormControl(null),
      energyValue: new UntypedFormControl(null, Validators.required),
      proteins: new UntypedFormControl(null, Validators.required),
      fats: new UntypedFormControl(null, Validators.required),
      saturatedFattyAcids: new UntypedFormControl(null),
      monoUnsaturatedFattyAcids: new UntypedFormControl(null),
      polyUnsaturatedFattyAcids: new UntypedFormControl(null),
      cholesterol: new UntypedFormControl(null),
      carbohydrates: new UntypedFormControl(null, Validators.required),
      sucrose: new UntypedFormControl(null),
      dietaryFibres: new UntypedFormControl(null),
      sodium: new UntypedFormControl(null),
      potassium: new UntypedFormControl(null),
      calcium: new UntypedFormControl(null),
      phosphorus: new UntypedFormControl(null),
      magnesium: new UntypedFormControl(null),
      iron: new UntypedFormControl(null),
      selenium: new UntypedFormControl(null),
      betaCarotene: new UntypedFormControl(null),
      vitaminD: new UntypedFormControl(null),
      vitaminC: new UntypedFormControl(null),
    }),
    amountTypes: new UntypedFormArray([]),
    allergenTypes: new UntypedFormControl(null)
  });

  initializeFormGroup() {
    this.clearForm();
    (<UntypedFormArray>this.form.get('amountTypes')).push(this.addAmountTypeFormGroup());

    this.form.setValue({
      id: null,
      category: {
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
      allergenTypes: null
    })
  }

  addAmountTypeFormGroup(): UntypedFormGroup {
    return new UntypedFormGroup({
      amountType: new UntypedFormControl(null, Validators.required),
      grams: new UntypedFormControl(null, Validators.required),
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
    for (const amountType of product.amountTypes) {
      (<UntypedFormArray>this.form.get('amountTypes')).push(this.addAmountTypeFormGroup());
    }

    this.form.setValue(product);
  }

  clearForm() {
    (<UntypedFormArray>this.form.get('amountTypes')).clear();
    this.form.reset();
  }
}
