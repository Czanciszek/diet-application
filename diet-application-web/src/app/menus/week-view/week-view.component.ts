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
import {ProductService} from "../../service/product.service";

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
    private mealService: MealService,
    private productService: ProductService
  ) {};

  menuItemData: any;
  weekMealItemData: any;
  dayMealListItemData: any;
  mealListItemData: any;
  dataLoaded = false;
  weekIndex: number;

  weekLoaded = false;
  dayLoaded = false;
  mealsLoaded = false;
  productMapLoaded = false;

  ngOnInit(): void {
    let menuId = this.route.snapshot.paramMap.get("menu_id");
    this.weekIndex = 0;
    this.getMenuDetails(menuId);
  }

  checkDataLoaded() {
    if (this.weekLoaded && this.dayLoaded && this.mealsLoaded && this.productMapLoaded) {
      this.dataLoaded = true;
    }
  }

  getMenuDetails(menuId) {
    this.menuService.getMenuById(menuId)
      .subscribe(
        (data: Menu[]) => {
          this.menuItemData = { ...data};
          if (this.menuItemData.weekMealList.length > 0) {
            let weekMealId = this.menuItemData.weekMealList[this.weekIndex];
            this.loadWeekData(weekMealId);
          }
        }
      );
  }

  loadWeekData(weekMealId) {
    this.getWeekMealDetails(weekMealId);
    this.getDayMealListDetails(weekMealId);
    this.getMealListDetails(weekMealId);
    this.getMenuProductMap();
  }

  swapWeek(next: Boolean) {
    if (next && (this.menuItemData.weekMealList.length > (this.weekIndex + 1))) {
      this.weekIndex += 1;
    } else if (!next && (this.weekIndex > 0)) {
      this.weekIndex -= 1;
    } else {
      return;
    }

    this.mealListItemData = [];
    let weekMealId = this.menuItemData.weekMealList[this.weekIndex];
    this.loadWeekData(weekMealId);
  }

  getWeekMealDetails(weekMealId) {
    this.weekMealService.getWeekMealById(weekMealId)
      .subscribe(
        (data: WeekMeal[]) => {
          this.weekMealItemData = {...data};
          this.weekLoaded = true;
          this.checkDataLoaded();
        });
  }

  getDayMealListDetails(weekMealId) {
    this.dayMealService.getDayMealListByWeekMealId(weekMealId)
      .subscribe(
        (daysData: DayMeal[]) => {
          this.dayMealListItemData = [...daysData];
          this.dayLoaded = true;
          this.checkDataLoaded();
        });
  }

  getMealListDetails(weekMealId) {
    this.mealService.getMealListByWeekMealId(weekMealId)
      .subscribe(
        (mealsData: Meal[]) => {
          this.mealListItemData = [...mealsData];
          this.mealsLoaded = true;
          this.checkDataLoaded();
        });
  }

  getMenuProductMap() {
    this.productService.getMenuProducts(this.menuItemData.id)
      .subscribe(
        (data: {} ) => {
          this.productService.menuProductMap = {...data};
          this.productMapLoaded = true;
          this.checkDataLoaded();
        });
  }

  translateDayType(name) {
    return translateDayType(name);
  }

  refreshMealList() {
    this.loadWeekData(this.weekMealItemData.id);
  }

  getFoodPropertiesDaySummary(day: DayMeal, property: string) {
    if (day.mealList == null)
      return this.setLimitStatus(0, property);

    let dayMeals = day.mealList;
    let meals = this.mealListItemData.filter( meal => {
      return dayMeals.includes(meal.id);
    });

    return this.getFoodProperties(meals, property);
  }

  getFoodProperties(meals: Meal[], property: string) {

    let value = 0;
    for (let meal of meals) {
      let isProduct = (meal.isProduct == 1);

      for (let product of meal.productList) {
        let grams = product.grams;

        if (this.productService.menuProductMap[product.productId] == null) {
          continue;
        }
        let foodProperties = this.productService.menuProductMap[product.productId].foodProperties;
        let productValue = (foodProperties[property] * grams) / 100;
        if (!isProduct) {
          productValue /= meal.portions;
        }

        value += productValue;
      }
    }

    return this.setLimitStatus(value, property);
  }

  setLimitStatus(value: number, property: string) {
    if (property == "energyValue") {
      let energyLimit = this.menuItemData.foodPropertiesType.energyValue;
      return "Kcal: " + value.toFixed(2) + "/" + energyLimit.toFixed(2);
    } else if (property == "proteins") {
      let proteinsLimit = this.menuItemData.foodPropertiesType.proteins;
      return "B: " + value.toFixed(2) + "/" + proteinsLimit.toFixed(2);
    } else if (property == "fats") {
      let fatsLimit = this.menuItemData.foodPropertiesType.fats;
      return "T: " + value.toFixed(2) + "/" + fatsLimit.toFixed(2);
    } else if (property == "carbohydrates") {
      let carbohydratesLimit = this.menuItemData.foodPropertiesType.carbohydrates;
      return "W: " + value.toFixed(2) + "/" + carbohydratesLimit.toFixed(2);
    }
  }

}
