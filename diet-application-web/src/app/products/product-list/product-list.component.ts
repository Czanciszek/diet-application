import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from "@angular/material/table";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { MatSort } from "@angular/material/sort";
import { MatPaginator } from "@angular/material/paginator";

import { ProductComponent } from "../product/product.component";
import { ProductService } from "../../service/product.service";
import { NotificationService } from "../../service/notification.service";
import { FileService } from "../../service/file.service";
import { Product } from "../../model/product";

@Component({
    selector: 'app-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css'],
    standalone: false
})
export class ProductListComponent implements OnInit {

  constructor(
    private productService: ProductService,
    private fileService: FileService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<Product>;
  displayedColumns: string[] = ['category', 'subcategory', 'name',
    'energyValue', 'proteins', 'fats', 'carbohydrates',
    'alergens', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.setUploadButton();

    this.getProductList();
  }

  getProductList() {
    this.onSearchClear();
    this.productService.getProducts()
      .subscribe(
        (data: Product[]) => {

          this.productService.productList = data.map(productData => {
            return new Product(
              productData['id'],
              productData['name'],
              productData['category'],
              productData['foodProperties'],
              productData['amountTypes'],
              productData['allergenTypes'],
            )
          });

          this.listData = new MatTableDataSource(this.productService.productList);
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;

          this.listData.filterPredicate = (data: Product, filter: string) => {
            return data.name == null || data.name.toLowerCase().includes(filter) ||
              data.category.category == null || data.category.category.toLowerCase().includes(filter) ||
              data.category.subcategory == null || data.category.subcategory.toLowerCase().includes(filter);
          };
        });
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    if (this.listData != null) {
      this.listData.filter = this.searchKey.trim().toLowerCase();
    }
  }

  onCreate() {
    this.productService.initializeFormGroup();
    this.showProductDialog(null);
  }

  onEdit(product) {
    this.productService.populateForm(product);
    this.showProductDialog(product);
  }

  showProductDialog(product) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "90%";

    const dialogRef = this.dialog.open(ProductComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(() => {

      if (product != null) {

        let listDataProduct = this.listData.data.find(x => x.id == product.id);
        let index = this.listData.data.indexOf(listDataProduct);
        this.listData.data[index] = this.productService.form.value;
        this.listData.data = this.listData.data;

      } else {
        this.getProductList();
      }

    });
  }

  onDelete(productId) {
    if (!confirm("Na pewno chcesz usunąć ten produkt?")) {
      return;
    }

    this.productService.deleteProduct(productId).subscribe(
      result => {
        this.notificationService.warn(":: Usunięto pomyślnie! ::");
      }, error => {
        this.notificationService.error(":: Wystąpił błąd podczas usuwania! ::");
      }, () => {
        this.getProductList();
      }
    );
  }

  onDownloadTemplate() {
    document.getElementById('template_download').click();
  }

  onUploadFile(file: File) {
    if (file != null) {
      this.fileService.uploadFile(file).subscribe(result => {
        this.getProductList();
      });
    }
  }

  setUploadButton() {
    const realFileBtn = (<HTMLInputElement>document.getElementById('real_input'));
    const uploadBtn = document.getElementById('button_upload');

    uploadBtn.addEventListener("click", function () {
      realFileBtn.click();
    });

    realFileBtn.addEventListener('change', (event) => {
      const files = (<HTMLInputElement>event.target).files;
      if (files.length > 0) {
        this.onUploadFile(files[0]);
      }
      realFileBtn.value = '';
    });
  }

  onRefreshButtonClick() {
    this.getProductList();
  }

}
