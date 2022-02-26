import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpParams, HttpRequest} from "@angular/common/http";
import { FormControl, FormGroup, Validators} from "@angular/forms";
import {GlobalVariable} from "../global";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {

    headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)
    });

    httpOptions = {
      headers: this.headers
    };

  constructor(
    private http: HttpClient
  ) { }

  form: FormGroup = new FormGroup({
    menuId: new FormControl(null),
    showDates: new FormControl(null),
    generateRecipes: new FormControl(null),
    generateShoppingList: new FormControl(null),
    recommendations: new FormControl(null)
  });

  initializeFormGroup() {
    this.form.setValue({
      menuId: null,
      showDates: true,
      generateRecipes: true,
      generateShoppingList: true,
      recommendations: null
    })
  }

  uploadFile(file: File): Observable<HttpEvent<any>> {

    let formData = new FormData();
    formData.append('upload', file);

    const url = GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE +
      "files/uploadProducts"
    const req = new HttpRequest('POST', url, formData, this.httpOptions);
    return this.http.request(req);

  }

  downloadFile(data: any, type: string) {
     let blob = new Blob([data], { type: type});
     let url = window.URL.createObjectURL(blob);
     let pwa = window.open(url);
     if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
         alert( 'Please disable your Pop-up blocker and try again.');
     }
  }

  getPdfFile(): any {
      this.http.post(GlobalVariable.SERVER_ADDRESS + GlobalVariable.DATABASE_SERVICE + "files/menu/", this.form.value,
      { responseType: 'arraybuffer', headers: this.headers}).subscribe(
          (response) => {
            this.downloadFile(response, "application/pdf");
      });
  }

}
