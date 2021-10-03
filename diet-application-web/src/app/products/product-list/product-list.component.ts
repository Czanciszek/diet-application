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
    private service: ProductService,
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
    this.service.getProducts().subscribe(
      list => {
        let array = list.map(item => {
          return {
            id: item.id,
            name: item.name,
            category: item.category,
            subcategory: item.subcategory,
            foodProperties: item.foodProperties,
            lactose: item.lactose,
            starch: item.starch,
            gluten: item.gluten,
          };
        });
        this.listData = new MatTableDataSource(array);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;

        this.listData.filterPredicate = (data: Product, filter: string) => {
          return data.name == null ||
            data.name.includes(filter) ||
            data.category.includes(filter) ||
            data.subcategory.includes(filter);
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
    this.service.initializeFormGroup();
    this.showProductDialog(null);
  }

  onEdit(product) {
    this.service.populateForm(product);
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
        this.listData.data[index] = this.service.form.value;
        this.listData.data = this.listData.data;

      } else {
        this.getProductList();
      }

      this.service.form.reset();
      this.service.initializeFormGroup();
    });
  }

  onDelete(productId) {
    if (confirm("Are you sure to delete this product?")) {
      this.service.deleteProduct(productId).subscribe( result => {

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
