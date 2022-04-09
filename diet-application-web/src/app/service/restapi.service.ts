import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {Observable} from "rxjs";
import {LocalStorageService} from "./local-storage.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  constructor(
    private http: HttpClient,
    private localStorageService: LocalStorageService,
    private router: Router
  ) { }

  loginHeaders(encryptedAuth: string) {
    return {
      headers: new HttpHeaders({
        AuthEncrypt: encryptedAuth
      }),
      responseType: 'text' as 'json'
    };
  }

  httpHeaders(responseType: any = null) {
    const token = this.localStorageService.get("token");
    return {
      headers: new HttpHeaders({
        Authorization: "Bearer " + token
      }),
      responseType: responseType
    };
  }

  public login(encryptedAuth:string) {
    return this.http.get(GlobalVariable.SERVER_ADDRESS + "auth/login", this.loginHeaders(encryptedAuth));
  }

  public get<T>(path: string): Observable<T> {
    let get = this.http.get<T>(
      GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      this.httpHeaders());

     this.observeResponse(get);
     return get;
  }

  public post(path: string, body: any, responseType: any = null) {
    return this.http.post(
      GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      body,
      this.httpHeaders(responseType));
  }

  public put(path: string, body: any) {
    return this.http.put(
      GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      body,
      this.httpHeaders());
  }

  public delete(path: string) {
    return this.http.delete(
      GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + path,
      this.httpHeaders());
  }

  observeResponse(observable) {
    observable
      .subscribe(
        (value) => { },
        error => {
          this.handleError(error);
        }
      );
  }

  handleError(error) {
    if (error.status === 401) {
      this.localStorageService.remove("token");
      this.router.navigate(["/"]);
    }
  }
}
