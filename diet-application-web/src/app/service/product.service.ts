import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic ' +
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)
    })
  };

  constructor(
    private http: HttpClient
  ) {
  }

  productList: any;
  menuProductMap: any;

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    category: new FormControl('', Validators.required),
    subcategory: new FormControl('', Validators.required),
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
    this.productList = this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "products", this.httpOptions);
    return this.productList;
  }

  getProductsByDishId(dishId) {
    this.productList = this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "products/dishlist/" + dishId, this.httpOptions);
    return this.productList;
  }

  getMenuProducts(menuId) {
    let productsMapData: any;
    productsMapData = this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "products/menu/" + menuId, this.httpOptions);
    return productsMapData;
  }

  getCategories() {
    return this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "categories", this.httpOptions);
  }

  getFilteredProducts(category: string, subcategory: string) {
    let categoryName = (category != null) ? category : "*ANY*";
    let subcategoryName = (subcategory != null) ? subcategory : "*ANY*";
    this.productList = this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "products/" + categoryName + "/" + subcategoryName, this.httpOptions);
    return this.productList;
  }

  insertProduct(product) {
    return this.http.post(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "products", product, this.httpOptions);
  }

  updateProduct(product) {
    return this.http.put(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "products/" + product.id, product, this.httpOptions);
  }

  deleteProduct(id: string) {
    return this.http.delete(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "products/" + id, this.httpOptions).subscribe();
  }

  populateForm(product) {
    this.form.setValue(product);
  }
}
