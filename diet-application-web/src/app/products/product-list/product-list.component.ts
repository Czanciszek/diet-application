import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ProductComponent} from "../product/product.component";
import {NotificationService} from "../../service/notification.service";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {FileService} from "../../service/file.service";
import {Product} from "../../model/product";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  constructor(
    private productService: ProductService,
    private fileService: FileService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;
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
        (data: Product[] ) => {

          this.productService.productList = [...data];
          this.listData = new MatTableDataSource(this.productService.productList);
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;

          this.listData.filterPredicate = (data: Product, filter: string) => {
          return data.name == null ||
            data.name.toLowerCase().includes(filter) ||
            data.category.toLowerCase().includes(filter) ||
            data.subcategory.toLowerCase().includes(filter);
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

        let listDataProduct = this.listData.data.find( x => x.id == product.id);
        let index = this.listData.data.indexOf(listDataProduct);
        this.listData.data[index] = this.productService.form.value;
        this.listData.data = this.listData.data;

      } else {
        this.getProductList();
      }

      this.productService.form.reset();
      this.productService.initializeFormGroup();
    });
  }

  onDelete(productId) {
    if (confirm("Are you sure to delete this product?")) {
      this.productService.deleteProduct(productId).subscribe( result => {

        let listDataProduct = this.listData.data.find( x => x.id == productId);
        let index = this.listData.data.indexOf(listDataProduct);
        this.listData.data.splice(index, 1);
        this.listData.data = this.listData.data;

        this.notificationService.warn(":: Deleted succesfully! ::");
      });
    }
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
    const realFileBtn =  (<HTMLInputElement>document.getElementById('real_input'));
    const uploadBtn = document.getElementById('button_upload');

    uploadBtn.addEventListener("click", function() {
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

  onRefresh() {
    this.getProductList();
  }

}
