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
    header: new FormControl(null),
    primaryImageId: new FormControl(null),
    type: new FormControl(''),
    category: new FormControl('', Validators.required),
    subcategory: new FormControl('', Validators.required),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    foodProperties: new FormGroup({
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
      header: null,
      primaryImageId: null,
      type: '',
      category: '',
      subcategory: '',
      name: '',
      foodProperties: {
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
    this.productList = this.http.get("http://localhost:8080/api/v1/products", this.httpOptions);
    return this.productList;
  }

  getMenuProducts(menuId) {
    let productsMapData: any;
    productsMapData = this.http.get("http://localhost:8080/api/v1/products/menu/" + menuId, this.httpOptions);
    return productsMapData;
  }

  insertProduct(product) {
    return this.http.post("http://localhost:8080/api/v1/products", product, this.httpOptions);
  }

  updateProduct(product) {
    return this.http.put("http://localhost:8080/api/v1/products/" + product.id, product, this.httpOptions);
  }

  getCategories() {
    return this.http.get("http://localhost:8080/api/v1/categories", this.httpOptions);
  }

  getFilteredProducts(category: string, subcategory: string) {
    if (category == null) {
      category = "*ANY*";
    }
    if (subcategory == null) {
      subcategory = "*ANY*";
    }
    this.productList = this.http.get("http://localhost:8080/api/v1/products/" + category + "/" + subcategory, this.httpOptions);
    return this.productList;
  }

  getFilteredProductsByName(name:string) {
    return this.http.get("http://localhost:8080/api/v1/products/name/" + name , this.httpOptions);
  }

  deleteProduct(id: string) {
    return this.http.delete("http://localhost:8080/api/v1/products/" + id, this.httpOptions).subscribe();
  }

  populateForm(product) {
    this.form.setValue(product);
  }
}
