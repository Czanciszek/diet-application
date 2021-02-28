import {Component, OnInit} from '@angular/core';
import {MenuService} from "../../service/menu.service";
import {WeekMealService} from "../../service/week-meal.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Menu} from "../../model/menu";
import {WeekMeal} from "../../model/week-meal";
import {DayMealService} from "../../service/day-meal.service";
import {DayMeal} from "../../model/day-meal";
import {translateDayType} from "../../material/helper/polish-translate";
import {MealService} from "../../service/meal.service";
import {Meal} from "../../model/meal";

@Component({
  selector: 'week-view.component',
  styleUrls: ['week-view.component.css'],
  templateUrl: 'week-view.component.html',
})
export class WeekViewComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private menuService: MenuService,
    private weekMealService: WeekMealService,
    private dayMealService: DayMealService,
    private mealService: MealService
  ) {};

  menuItemData: any;
  weekMealItemData: any;
  dayMealListItemData: any;
  mealListItemData: any;
  dataLoaded = false;

  ngOnInit(): void {
    let menuId = this.route.snapshot.paramMap.get("menu_id");

    this.getMenuDetails(menuId);
  }

  getMenuDetails(menuId) {
    this.menuService.getMenuById(menuId)
      .subscribe(
        (data: Menu[]) => {
          this.menuItemData = { ...data};
          let weekMealId = this.menuItemData.weekMealList[0];
          console.log("Menu", this.menuItemData);
          this.getWeekMealDetails(weekMealId);
        }
      );
  }

  getWeekMealDetails(weekMealId) {
    this.weekMealService.getWeekMealById(weekMealId)
      .subscribe(
        (data: WeekMeal[]) => {
          this.weekMealItemData = {...data};
          console.log("Week", this.weekMealItemData);
          this.getDayMealListDetails(this.weekMealItemData.dayMealList);
        });
  }

  getDayMealListDetails(daysListId) {
    this.dayMealService.getDayMealListByListId(daysListId)
      .subscribe(
        (daysData: DayMeal[]) => {
          this.dayMealListItemData = [...daysData];
          console.log("Days", this.dayMealListItemData);
          this.getMealListDetails(daysListId);
        });
  }

  getMealListDetails(daysListId) {
    this.mealService.getMealListByListId(daysListId)
      .subscribe(
        (mealsData: Meal[]) => {
          this.mealListItemData = [...mealsData];
          console.log("Meals", this.mealListItemData);
          this.dataLoaded = true;
        });
  }

  translateDayType(name) {
    return translateDayType(name);
  }

  refreshMealList() {
    this.getMealListDetails(this.weekMealItemData.dayMealList);
  }

}
