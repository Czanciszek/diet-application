import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { DishService } from "../../service/dish.service";
import { MatLegacyDialogRef as MatDialogRef, MAT_LEGACY_DIALOG_DATA as MAT_DIALOG_DATA } from "@angular/material/legacy-dialog";
import { MatLegacyTableDataSource as MatTableDataSource } from "@angular/material/legacy-table";
import { MatSort } from "@angular/material/sort";
import { MatLegacyPaginator as MatPaginator } from "@angular/material/legacy-paginator";
import { Dish } from "../../model/dish";
import { DishUsage } from "../../model/dishUsage";
import { FOOD_TYPES } from "../../model/helpers/foodTypes";

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
    this.mapDishUsageInfo();
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
    Object(dishData).forEach((key, index) => {
      dishData[index].dishUsages = 0;
      dishUsages
        .filter(dishUsage => dishUsage.dishId == dishData[index].id)
        .forEach((dishUsageKey, dishUsageIndex) => {
          dishData[index].dishUsages += dishUsageKey.dishUsage;
        });
      if (dishData[index].dishUsages == 0) {
        dishData[index].dishUsages = "-";
      }
    });
  }

  mapDishUsageInfo() {
    let dishData = this.listData.data;
    const dishUsages = this.dialogData.dishUsages;
    Object(dishData).forEach((key, index) => {
      let dishUsageInfo = "";
      dishUsages
        .filter(dishUsage => dishUsage.dishId == dishData[index].id)
        .forEach((dishUsageKey, dishUsageIndex) => {
          dishUsageInfo += this.parseDate(dishUsageKey.startDate) + " - " + this.parseDate(dishUsageKey.endDate) + " - " + dishUsageKey.dishUsage + "x \n";
        });
      dishData[index].dishUsageInfo = dishUsageInfo;
    });
  }

  parseDate(plainDate) {
    var date = new Date(plainDate);
    return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();
  }

  getFoodType(foodTypeId) {
    if (foodTypeId == null) return;
    let foodType = FOOD_TYPES.filter(x => x.id == foodTypeId);
    return foodType[0].value;
  }
}
