import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

import {RestapiService} from "./restapi.service";

import {Product} from '../model/product';

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
    category: new FormControl('', Validators.required),
    subcategory: new FormControl('', Validators.required),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    foodProperties: new FormGroup({
      id: new FormControl(null),
      energyValue: new FormControl(null, Validators.required),
      proteins: new FormControl(null, Validators.required),
      fats: new FormControl(null, Validators.required),
      saturatedFattyAcids: new FormControl(null),
      monoUnsaturatedFattyAcids: new FormControl(null),
      polyUnsaturatedFattyAcids: new FormControl(null),
      cholesterol: new FormControl(null),
      carbohydrates: new FormControl(null, Validators.required),
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
    lactose: new FormControl(false),
    starch: new FormControl(false),
    gluten: new FormControl(false),
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      category: '',
      subcategory: '',
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
      lactose: false,
      starch: false,
      gluten: false,
    })
  }

  getProducts() {
    return this.restApiService.get("products");
  }

  getProductsByDishId(dishId) {
    return this.restApiService.get("products/dishlist/" + dishId);
  }

  getCategories() {
    return this.restApiService.get("categories");
  }

  getFilteredProducts(category: string, subcategory: string) {
    let categoryName = (category != null) ? category : "*ANY*";
    let subcategoryName = (subcategory != null) ? subcategory : "*ANY*";

    return this.restApiService.get( "products/" + categoryName + "/" + subcategoryName);
  }

  insertProduct(product) {
    return this.restApiService.post("products", product);
  }

  updateProduct(product) {
    return this.restApiService.put( "products/" + product.id, product);
  }

  deleteProduct(id: string) {
    return this.restApiService.delete( "products/" + id);
  }

  populateForm(product) {
    this.form.setValue(product);
  }
}
