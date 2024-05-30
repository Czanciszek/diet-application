import { Injectable } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup } from "@angular/forms";
import { RestapiService } from "../service/restapi.service";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(
    private apiService: RestapiService
  ) { }

  form: UntypedFormGroup = new UntypedFormGroup({
    menuId: new UntypedFormControl(null),
    showDates: new UntypedFormControl(null),
    generateRecipes: new UntypedFormControl(null),
    generateShoppingList: new UntypedFormControl(null),
    recommendations: new UntypedFormControl(null)
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

  uploadFile(file: File) {

    let formData = new FormData();
    formData.append('upload', file);

    return this.apiService.post(formData, "files/uploadProducts");
  }

  downloadFile(data: any, type: string) {
    let blob = new Blob([data], { type: type });
    let url = window.URL.createObjectURL(blob);
    let pwa = window.open(url);
    if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
      alert('Please disable your Pop-up blocker and try again.');
    }
  }

  getPdfFile() {
    return this.apiService
      .post(this.form.value, "files/menu", "v1", 'arraybuffer');
  }

}
