import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  form: FormGroup = new FormGroup({
    $key: new FormControl(null),
    category: new FormControl('', Validators.required),
    subcategory: new FormControl('', Validators.required),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    hasLactose: new FormControl(false),
  });

  initializeFormGroup() {
    this.form.setValue({
      $key: null,
      category: '',
      subcategory: '',
      name: '',
      hasLactose: false
    })
  }

  public getProducts() {
    return this.http.get("http://localhost:8080/products", this.httpOptions);
  }

  public getCategories() {
    return this.http.get("http://localhost:8080/categories", this.httpOptions);
  }

  public getFilteredProducts(category:string, subcategory:string) {
    if (category == null) {
      category = "*ANY*";
    }
    if (subcategory == null) {
      subcategory = "*ANY*";
    }
    return this.http.get("http://localhost:8080/products/" + category + "/" + subcategory, this.httpOptions);
  }

  public getFilteredProductsByName(name:string) {
    return this.http.get("http://localhost:8080/products/name/" + name , this.httpOptions);
  }
}
