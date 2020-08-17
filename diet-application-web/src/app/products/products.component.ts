import {Component, Inject, OnInit} from '@angular/core';
import {RestapiService} from "../service/restapi.service";

import {DOCUMENT} from "@angular/common";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ProductService} from "../service/product.service";
import {ProductComponent} from "./product/product.component";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
})
export class ProductsComponent implements OnInit {

  products: any;
  categories: any;
  subcategories: any = [];

  selectedCategory: any;
  selectedSubcategory: any;

  constructor(
    @Inject(DOCUMENT) document,
    private service: ProductService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.getCategories();
  }

  getProducts() {
    let response = this.service.getFilteredProducts(this.selectedCategory, this.selectedSubcategory);
    response.subscribe(data => {
      (<HTMLInputElement>document.getElementById("productName")).value = '';
      this.products = data;
    });
  }

  getProductsByName() {
    let name = (<HTMLInputElement>document.getElementById("productName")).value;
    if (name == "") return;
    let response = this.service.getFilteredProductsByName(name);
    response.subscribe(data => {
      (<HTMLInputElement>document.getElementById("selectedCategory")).value = '';
      (<HTMLInputElement>document.getElementById("selectedSubcategory")).value = '';
      this.selectedCategory = null;
      this.selectedSubcategory = null;
      this.products = data;
    });
  }

  getCategories() {
    let response = this.service.getCategories();
    response.subscribe(data => {
      this.categories = data;
      for (const key of Object.values(data))
        this.subcategories = this.subcategories.concat(key.subcategories);
    });
  }

  getSubcategories() {
    let subcategories = this.categories.find( cat => {
      return cat.category == this.selectedCategory;
    });
    if (subcategories == undefined) {
      this.subcategories = null;
    } else {
      this.subcategories = subcategories.subcategories;
    }
  }

  checkCategoryValue() {
    let value = (<HTMLInputElement>document.getElementById("selectedCategory")).value;
    if (value == null || value == "" || value.length < 1) {
      this.selectedCategory = null;
    }
  }

  checkSubcategoryValue() {
    let value = (<HTMLInputElement>document.getElementById("selectedSubcategory")).value;
    if (value == null || value == "" || value.length < 1) {
      this.selectedSubcategory = null;
    }
  }

  setCategory() {
    let newCategory = (<HTMLInputElement>document.getElementById("selectedCategory")).value;
    if (newCategory !== this.selectedCategory) {
      (<HTMLInputElement>document.getElementById("selectedSubcategory")).value = '';
      this.selectedSubcategory = null;
      this.selectedCategory = newCategory;
    }
    this.getSubcategories();
  }

  setSubcategory() {
    this.selectedSubcategory = (<HTMLInputElement>document.getElementById("selectedSubcategory")).value;
  }

  onCreate() {
    this.service.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "90%";

    this.dialog.open(ProductComponent, dialogConfig);
  }

  onEdit(product) {
    this.service.populateForm(product);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "90%";

    this.dialog.open(ProductComponent, dialogConfig);
  }

  onDelete(productId) {
    if (confirm("Are you sure to delete this product?"))
      this.service.deleteProduct(productId);
  }

}
