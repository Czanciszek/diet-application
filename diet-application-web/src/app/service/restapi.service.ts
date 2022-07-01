import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {LocalStorageService} from "./local-storage.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RestapiService {

  SERVER_ADDRESS = 'https://dietapp.loca.lt:8080/';

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
    return this.http.get(
      this.getPath("auth/login", "v1", false),
      this.loginHeaders(encryptedAuth));
  }

  public register(registerForm: any) {
    return this.http.post(
      this.getPath("auth/register", "v1", false),
      registerForm,
      this.httpHeaders());
  }

  public get<T>(path: string, version: string = "v1"): Observable<T> {
    let get = this.http.get<T>(
      this.getPath(path, version),
      this.httpHeaders());

     this.observeResponse(get);
     return get;
  }

  public post(body: any, path: string, version: string = "v1", responseType: any = null) {
    return this.http.post(
      this.getPath(path, version),
      body,
      this.httpHeaders(responseType));
  }

  public put(body: any, path: string, version: string = "v1") {
    return this.http.put(
      this.getPath(path, version),
      body,
      this.httpHeaders());
  }

  public delete(path: string, version: string = "v1") {
    return this.http.delete(
      this.getPath(path, version),
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

  getPath(path: string, version: string, useApi: boolean = true) {
    let url = this.SERVER_ADDRESS;
    if (useApi) url += "api/";
    url += version + "/" + path;
    return url
  }
}
