import { Component, OnInit } from '@angular/core';
import { ProductService } from "../../service/product.service";
import { FormArray } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";
import { NotificationService } from "../../service/notification.service";
import { AMOUNT_TYPES } from "../../model/helpers/amountTypes";
import { ALLERGEN_TYPES } from "../../model/helpers/allergenTypes";
import { ProductCategory, ProductCategorySet } from "../../model/product-category";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  amountTypes = AMOUNT_TYPES;
  allergens = ALLERGEN_TYPES;

  categoryList: ProductCategory[] = [];
  categories: any = new Set();
  subcategories: any = new Set();

  constructor(
    private productService: ProductService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<ProductComponent>
  ) { }

  productServiceForm = this.productService.form;

  showMoreProperties = false;

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories() {

    const productCategories = this.productService.productList.map(p => new ProductCategory(p.category.category, p.category.subcategory));
    const productCategorySet = new ProductCategorySet(productCategories);
    this.categoryList = productCategorySet.values();

    for (const key of this.categoryList) {
      this.categories.add(key.category);
      this.subcategories.add(key.subcategory);
    }
  }

  fillSubcategories(categories) {
    for (const key of categories) {
      this.subcategories.add(key.subcategory);
    }
  }

  onCategoryChange(category) {
    if (category == null) return;
    this.subcategories.clear();
    const filteredCategories = this.categoryList.filter(x => !!x.category && x.category.includes(category));
    this.fillSubcategories(filteredCategories);
    this.productServiceForm.get('category').get('subcategory').patchValue(null);
  }

  onSubcategoryChange(subcategory) {
    if (subcategory == null) return;
    const filteredCategory = this.categoryList.find(x => !!x.subcategory && x.subcategory.includes(subcategory));

    this.onCategoryChange(filteredCategory.category);
    this.productServiceForm.get('category').get('category').patchValue(filteredCategory.category);
    this.productServiceForm.get('category').get('subcategory').patchValue(subcategory);
  }

  onClear() {
    this.productService.clearForm();
  }

  onSubmit() {
    if (!this.productService.form.valid) {
      return;
    }

    this.trimProductName();

    if (!this.productService.form.get('id').value) {
      this.insertProduct();
    } else {
      this.updateProduct();
    }
  }

  insertProduct() {
    this.productService.insertProduct(this.productService.form.value).subscribe(
      _ => {
        this.notificationService.success(":: Produkt stworzony pomyślnie! ::");
        this.onClose();
      }, error => {
        this.handleError(error);
      }
    );
  }

  updateProduct() {
    this.productService.updateProduct(this.productService.form.value).subscribe(
      _ => {
        this.notificationService.success(":: Produkt zaktualizowany pomyślnie! ::");
        this.onClose();
      }, error => {
        this.handleError(error);
      }
    );
  }

  addAmountTypeButtonClick() {
    (<FormArray>this.productServiceForm.get('amountTypes')).push(this.productService.addAmountTypeFormGroup());
  }

  onAmountTypeDeleteButtonClick(amountTypeIndex) {
    (<FormArray>this.productServiceForm.get('amountTypes')).removeAt(amountTypeIndex);
  }

  trimProductName() {
    let productName = this.productServiceForm.get('name').value.trim();
    this.productServiceForm.get('name').patchValue(productName);
  }

  handleError(error) {
    // TODO: Handle reason of error
    this.notificationService.error(":: Wystąpił bład! ::");
  }

  onClose() {
    this.dialogRef.close();
  }
}
