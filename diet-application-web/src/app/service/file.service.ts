import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpParams, HttpRequest} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  uploadFile(file: File): Observable<HttpEvent<any>> {

    let formData = new FormData();
    formData.append('upload', file);

    const url = GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "files/uploadProducts"
    const req = new HttpRequest('POST', url, formData, this.httpOptions);
    return this.http.request(req);

  }

}
