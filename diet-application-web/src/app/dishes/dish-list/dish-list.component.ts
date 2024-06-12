import { Component, OnInit, ViewChild } from '@angular/core';
import { DishService } from "../../service/dish.service";
import { ProductService } from "../../service/product.service";
import { MatLegacyDialog as MatDialog, MatLegacyDialogRef as MatDialogRef } from "@angular/material/legacy-dialog";
import { NotificationService } from "../../service/notification.service";
import { MatLegacyTableDataSource as MatTableDataSource } from "@angular/material/legacy-table";
import { MatSort } from "@angular/material/sort";
import { MatLegacyPaginator as MatPaginator } from "@angular/material/legacy-paginator";
import { DishComponent } from "../dish/dish.component";
import { FOOD_TYPES } from "../../model/helpers/foodTypes";
import { Dish } from "../../model/dish";
import { Product } from "../../model/product";

@Component({
  selector: 'app-dish-list',
  templateUrl: './dish-list.component.html',
  styleUrls: ['./dish-list.component.css']
})
export class DishListComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DishListComponent>,
    private dialog: MatDialog,
    private dishService: DishService,
    private productService: ProductService,
    private notificationService: NotificationService,
  ) { }

  listData: MatTableDataSource<Dish>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string = "";

  ngOnInit(): void {
    this.getDishList();
    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts()
      .subscribe(
        (data: Product[]) => {
          this.productService.productList = [...data];
        });
  }

  getDishList() {
    this.dishService.getDishes().subscribe(
      (data: Dish[]) => {
        this.fetchResults(data);
      });
  }

  fetchResults(data: Dish[]) {

    this.dishService.dishList = data.map(dishData => {
      return new Dish(
        dishData['id'],
        dishData['name'],
        dishData['products'],
        dishData['portions'],
        dishData['recipe'],
        dishData['foodType'],
      )
    });

    this.listData = new MatTableDataSource(this.dishService.dishList);
    this.listData.sort = this.sort;
    this.listData.paginator = this.paginator;

    this.listData.filterPredicate = (data: Dish, filter: string) => {
      return data.name == null || data.name.toLowerCase().includes(filter) ||
        data.foodType == null || data.foodType.toLowerCase().includes(filter);
    };
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onNewDishButtonClick() {
    this.dishService.initializeFormGroup();
    this.openEditDishDialog();
  }

  onEdit(dish) {
    this.dishService.populateForm(dish);
    this.openEditDishDialog();
  }

  openEditDishDialog() {
    let dialogRef = this.dialog.open(DishComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(_ => {
      this.getDishList();
    });
  }

  onClose() {
    this.dialogRef.close();
  }

  onDelete(dishId) {
    if (!confirm("Na pewno chcesz usunąć potrawę?")) {
      return;
    }

    this.dishService.deleteDish(dishId).subscribe(
      _ => {

        let listDataDish = this.listData.data.find(x => x.id == dishId);
        let index = this.listData.data.indexOf(listDataDish);
        this.listData.data.splice(index, 1);
        this.listData.data = this.listData.data;

        this.notificationService.warn(":: Usunięto pomyślnie! ::");
      }, _ => {
        this.notificationService.error(":: Wystąpił błąd podczas usuwania! ::");
      }, () => {
        this.getDishList();
      }
    );
  }

  getFoodType(foodTypeId) {
    if (foodTypeId == null) return;
    let foodType = FOOD_TYPES.filter(x => x.id == foodTypeId);
    return foodType[0].value;
  }
}
