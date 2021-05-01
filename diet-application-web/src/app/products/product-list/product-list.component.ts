import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ProductComponent} from "../product/product.component";
import {NotificationService} from "../../service/notification.service";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  constructor(
    private service: ProductService,
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
      });
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
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
    if (confirm("Are you sure to delete this product?")) {
      this.service.deleteProduct(productId);
      this.notificationService.warn(":: Deleted succesfully! ::");
    }
  }
}
