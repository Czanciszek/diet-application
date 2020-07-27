import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  public login(username:string, password:string) {
    const headers = new HttpHeaders({Authorization: 'Basic '+ btoa(username + ":" + password)});
    return this.http.get("http://localhost:8080/auth/login", {headers, responseType:'text' as 'json'});
  }

  public getUsers() {
    return this.http.get("http://localhost:8080/users", this.httpOptions);
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
