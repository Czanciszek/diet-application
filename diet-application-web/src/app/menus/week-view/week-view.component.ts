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
import {FormArray} from "@angular/forms";

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
    this.getWeekMealDetails(this.weekMealItemData.id);
  }

  getFoodPropertiesDaySummary(day: DayMeal) {
    if (day.mealList == null)
      return "";
    let dayMeals = day.mealList;
    let meals = this.mealListItemData.filter( meal => {
      return dayMeals.includes(meal.id);
    });

    return this.getFoodProperties(meals);
  }

  getFoodProperties(meals: Meal[]) {
    let grams = 0;
    let energy = 0;
    let proteins = 0;
    let fats = 0;
    let carbohydrates = 0;

    for (let meal of meals) {
      let isProduct = (meal.isProduct == 1);
      if (isProduct) {
        grams = meal.grams;
      }

      for (let product of meal.productForDishList) {
        if (!isProduct) {
          grams = product.grams;
        }

        let productEnergy = (product.foodProperties.energyValue * grams) / 100;
        let productProteins = (product.foodProperties.proteins * grams) / 100;
        let productFats = (product.foodProperties.fats * grams) / 100;
        let productCarbohydrates = (product.foodProperties.carbohydrates * grams) / 100;

        if (!isProduct) {
          let portions = meal.portions;
          productEnergy /= portions;
          productProteins /= portions;
          productFats /= portions;
          productCarbohydrates /= portions;
        }

        energy += productEnergy;
        proteins += productProteins;
        fats += productFats;
        carbohydrates += productCarbohydrates;
      }
    }

    return "Kcal: " + energy.toFixed(2) +
      "    B: " + proteins.toFixed(2) +
      "    T: " + fats.toFixed(2) +
      "    W: " + carbohydrates.toFixed(2);
  }

}
