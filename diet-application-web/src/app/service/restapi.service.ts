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
    return this.http.get("http://localhost:8080/api/v1/users", this.httpOptions);
  }


}
