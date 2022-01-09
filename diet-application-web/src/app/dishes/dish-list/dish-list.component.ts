import {Component, OnInit, ViewChild, Input} from '@angular/core';
import {DishService} from "../../service/dish.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {DishComponent} from "../dish/dish.component";
import {FormArray} from "@angular/forms";
import {FOOD_TYPES} from "../../model/helpers/foodTypes";
import {Dish} from "../../model/dish";
import {DishSelectComponent} from "../../dishes/dish-select/dish-select.component";

@Component({
  selector: 'app-dish-list',
  templateUrl: './dish-list.component.html',
  styleUrls: ['./dish-list.component.css']
})
export class DishListComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<DishListComponent>,
    private dialog: MatDialog,
    private service: DishService,
    private notificationService: NotificationService,
  ) { }

  public menuId: any;

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.fetchDishData();
  }

  fetchDishData() {
    if (this.menuId == null) {
      this.getDishList();
    } else {
      this.getDishListByMenuId();
    }
  }

  getDishList() {
    this.service.getDishes().subscribe(
      (data: Dish[]) => {
        this.fetchResults(data);
      });
  }

  getDishListByMenuId() {
    this.service.getDishesByMenuId(this.menuId).subscribe(
      (data: Dish[]) => {
        this.fetchResults(data);
      });
  }

  fetchResults(data: Dish[]) {
    this.listData = new MatTableDataSource([...data]);
    this.listData.sort = this.sort;
    this.listData.paginator = this.paginator;

    this.listData.filterPredicate = (data: Dish, filter: string) => {
      return data.name == null ||
        data.name.toLowerCase().includes(filter) ||
        data.foodType.toLowerCase().includes(filter);
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
    if (this.menuId != null) {
      this.addNewDishToMenu();
    } else {
      (<FormArray>this.service.form.get('products')).push(this.service.addProductFormGroup());
      this.service.initializeFormGroup();
      this.openEditDishDialog();
    }
  }

  onEdit(dish) {
    for (const product of dish.products) {
      (<FormArray>this.service.form.get('products')).push(this.service.addProductFormGroup());
    }
    this.service.populateForm(dish);
    this.openEditDishDialog();
  }

  openEditDishDialog() {
    let dialogRef = this.dialog.open(DishComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchDishData();
    });
  }

  addNewDishToMenu() {

    let dialogRef = this.dialog.open(DishSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null) {
        let dish = result;
        dish.menuId = this.menuId;
        this.copyDishToMenu(dish);
      }
    });
  }

  onClose() {
    this.dialogRef.close();
  }

  copyDishToMenu(dish) {
    this.service.copyDishToMenu(dish).subscribe(result => {
      this.fetchDishData();
    });
  }

  onDelete(dishId) {
    if (confirm("Na pewno chcesz usunąć potrawę?")) {
      this.service.deleteDish(dishId).subscribe( result => {

        let listDataDish = this.listData.data.find( x => x.id == dishId);
        let index = this.listData.data.indexOf(listDataDish);
        this.listData.data.splice(index, 1);
        this.listData.data = this.listData.data;

        this.notificationService.warn(":: Usunięto pomyślnie! ::");
      }, error => {
        this.notificationService.error(":: Wystąpił błąd podczas usuwania! ::");
      }, () => {
        this.fetchDishData();
      });

    }
  }

  getFoodType(foodTypeId) {
    let foodType = FOOD_TYPES.filter(x => x.id == foodTypeId);
    return foodType[0].value;
  }
}
