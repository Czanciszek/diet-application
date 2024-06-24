import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Sort, SortDirection, MatSort } from "@angular/material/sort";
import { PageEvent, MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";

import { DishService } from "../../service/dish.service";
import { DishSelectorService } from '../../service/dish-selector.service';

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
    private dishSelectorService: DishSelectorService,
    public dialogRef: MatDialogRef<DishSelectComponent>,
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'dishUsages', 'actions'];
  searchKey: string = "";
  pageIndex = 0;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit(): void {
    this.updateRecentSearch();
    this.fetchData();
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    if (this.searchKey == null) {
      return;
    }

    this.listData.filter = this.searchKey.trim().toLowerCase();
    this.dishSelectorService.lastInputString = this.searchKey;
  }

  fetchData() {
    this.getDishList();
  }

  getDishList() {
    this.dishService.getDishes().subscribe(
      (data: Dish[]) => {
        this.setFetchedResults(data);
        this.updateSortSelection();
        this.applyFilter();
      });
  }

  setFetchedResults(data: Dish[]) {
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

  handlePageEvent(e: PageEvent) {
    this.dishSelectorService.selectedPageIndex = e.pageIndex;
  }

  handleSelectedSort(sort: Sort) {
    this.dishSelectorService.selectedSort = sort;
  }

  private updateRecentSearch() {
    this.searchKey = this.dishSelectorService.lastInputString;
    this.pageIndex = this.dishSelectorService.selectedPageIndex;
  }

  private updateSortSelection() {
    if (this.dishSelectorService.selectedSort != null) {
      this.sort.active = this.dishSelectorService.selectedSort.active;
      this.sort.direction = this.dishSelectorService.selectedSort.direction;
    }
  }
}
