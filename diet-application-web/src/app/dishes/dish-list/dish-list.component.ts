import {Component, OnInit, ViewChild} from '@angular/core';
import {DishService} from "../../service/dish.service";
import {MatDialog} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {DishComponent} from "../dish/dish.component";
import {FormArray} from "@angular/forms";
import {FOOD_TYPES} from "../../model/helpers/foodTypes";
import {Dish} from "../../model/dish";

@Component({
  selector: 'app-dish-list',
  templateUrl: './dish-list.component.html',
  styleUrls: ['./dish-list.component.css']
})
export class DishListComponent implements OnInit {

  constructor(
    private service: DishService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.getDishList();
  }

  getDishList() {
    this.service.getDishes().subscribe(
      (data: Dish[]) => {

        this.listData = new MatTableDataSource( [ ...data]);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;

        this.listData.filterPredicate = (data: Dish, filter: string) => {
          return data.name == null ||
            data.name.toLowerCase().includes(filter) ||
            data.foodType.toLowerCase().includes(filter);
        };
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
    (<FormArray>this.service.form.get('products')).push(this.service.addProductFormGroup());
    this.service.initializeFormGroup();
    this.openDialog();
  }

  onEdit(dish) {
    for (const product of dish.products) {
      (<FormArray>this.service.form.get('products')).push(this.service.addProductFormGroup());
    }
    this.service.populateForm(dish);
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(DishComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.getDishList();
    });
  }

  onDelete(dishId) {
    if (confirm("Are you sure to delete this dish?")) {
      this.service.deleteDish(dishId).subscribe( result => {

        let listDataDish = this.listData.data.find( x => x.id == dishId);
        let index = this.listData.data.indexOf(listDataDish);
        this.listData.data.splice(index, 1);
        this.listData.data = this.listData.data;

        this.notificationService.warn(":: Deleted succesfully! ::");

      });

      this.getDishList();
    }
  }

  getFoodType(foodTypeId) {
    let foodType = FOOD_TYPES.filter(x => x.id == foodTypeId);
    return foodType[0].value;
  }
}
