import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../service/product.service";
import {MatDialogRef} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  categoryList: any[] = [];
  categories: any = new Set();
  subcategories: any = new Set();

  constructor(
    private service: ProductService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<ProductComponent>
  ) { }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories() {
    let response = this.service.getCategories();
    response.subscribe(data => {
      this.categoryList = Object.values(data);
      for (const key of this.categoryList) {
        this.categories.add(key.category);
        this.subcategories.add(key.subcategory);
      }
    });
  }

  fillSubcategories(categories) {
    for (const key of categories) {
      this.subcategories.add(key.subcategory);
    }
  }

  onCategoryChange(category) {
    if (category == null) return;
    this.subcategories.clear();
    const filteredCategories = this.categoryList.filter( x => !!x.category && x.category.includes(category));
    this.fillSubcategories(filteredCategories);
    this.service.form.controls['subcategory'].setValue(null);
  }

  onSubcategoryChange(subcategory) {
    if (subcategory == null) return;
    const filteredCategory = this.categoryList.find( x => !!x.subcategory && x.subcategory.includes(subcategory));

    this.onCategoryChange(filteredCategory.category);
    this.service.form.controls['category'].setValue(filteredCategory.category);
  }

  onClear() {
    this.service.form.reset();
    this.service.initializeFormGroup();
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (!this.service.form.get('id').value) {
        this.service.insertProduct(this.service.form.value).subscribe( result => {
          this.notificationService.success(":: Product created successfully! ::");
          this.onClose();
        });
      } else {
        this.service.updateProduct(this.service.form.value).subscribe( result => {
          this.notificationService.success(":: Product updated successfully! ::");
          this.onClose();
        });
      }
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
