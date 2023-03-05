import {Component, OnInit, ViewChild, Inject} from '@angular/core';
import {DishService} from "../../service/dish.service";
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {Dish} from "../../model/dish";
import {DishUsage} from "../../model/dishUsage";

export interface DishSelectDialogData {
  dishUsages: DishUsage[]
}

@Component({
  selector: 'app-dish-select',
  templateUrl: './dish-select.component.html',
  styleUrls: ['./dish-select.component.css']
})
export class DishSelectComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public dialogData: DishSelectDialogData,
    private dishService: DishService,
    public dialogRef: MatDialogRef<DishSelectComponent>,
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'dishUsages', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.fetchData();
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  fetchData() {
    this.getDishList();
  }

  getDishList() {
    this.dishService.getDishes().subscribe(
      (data: Dish[]) => {
        this.fetchResults(data);
      });
  }

  fetchResults(data: Dish[]) {
    this.listData = new MatTableDataSource([...data]);
    this.mapDishUsages();
    this.listData.sort = this.sort;
    this.listData.paginator = this.paginator;
  }

  onSelect(dish) {
    this.dialogRef.close(dish);
  }

  onClose() {
    this.dialogRef.close();
  }

  mapDishUsages() {
    let dishData = this.listData.data;
    const dishUsages = this.dialogData.dishUsages;
    Object(dishData).forEach( (key, index) => {
      dishData[index].dishUsages = 0;
      dishUsages.filter(dishUsage => dishUsage.dishId == dishData[index].id)
        .forEach( (dishUsageKey, dishUsageIndex) => {
          dishData[index].dishUsages += dishUsageKey.dishUsage;
        });
    });
  }

}
