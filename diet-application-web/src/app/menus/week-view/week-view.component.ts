import {Component, OnInit} from '@angular/core';
import {MenuService} from "../../service/menu.service";
import {WeekMealService} from "../../service/week-meal.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Menu} from "../../model/menu";
import {WeekMeal} from "../../model/week-meal";
import {DayMealService} from "../../service/day-meal.service";
import {DayMeal} from "../../model/day-meal";

@Component({
  selector: 'week-view.component',
  styleUrls: ['week-view.component.css'],
  templateUrl: 'week-view.component.html',
})
export class WeekViewComponent implements OnInit {

  days = [
    'Poniedziałek', 'Wtorek', 'Środa', 'Czwartek',
    'Piątek', 'Sobota', 'Niedziela'
  ];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private menuService: MenuService,
    private weekMealService: WeekMealService,
    private dayMealService: DayMealService
  ) {};

  menuItemData: any;
  weekMealItemData: any;
  dayMealListItemData: any;

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
          this.dayMealListItemData = {...daysData};
          console.log("Days", this.dayMealListItemData);
        });
  }

}
