import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {Observable} from "rxjs";
import {LocalStorageService} from "./local-storage.service";

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
    private http: HttpClient,
    private localStorage: LocalStorageService
  ) { }

  public login(username:string, password:string) {
    const headers = new HttpHeaders({Authorization: 'Basic '+ btoa(username + ":" + password)});
    return this.http.get("http://localhost:8080/auth/login", {headers, responseType:'text' as 'json'});
  }

  public get<T>(path: string): Observable<T> {
    return this.http.get<T>(GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      this.httpOptions);
  }

  public post(path: string, body: any) {
    return this.http.post(
      GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      body,
      this.httpOptions);
  }

  public put(path: string, body: any) {
    return this.http.put(
      GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      body,
      this.httpOptions);
  }

  public delete(path: string) {
    return this.http.delete(
      GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      this.httpOptions);
  }
}
