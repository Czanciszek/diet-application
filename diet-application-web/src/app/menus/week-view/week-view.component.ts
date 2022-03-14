import {Component, OnInit} from '@angular/core';
import {MenuService} from "../../service/menu.service";
import {WeekMealService} from "../../service/week-meal.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Menu} from "../../model/menu";
import {WeekMeal} from "../../model/week-meal";
import {DayMealService} from "../../service/day-meal.service";
import {NotificationService} from "../../service/notification.service";
import {DayMeal} from "../../model/day-meal";
import {translateDayType} from "../../material/helper/polish-translate";
import {MealService} from "../../service/meal.service";
import {Meal} from "../../model/meal";
import {Product} from "../../model/product";
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
    private productService: ProductService,
    private notificationService: NotificationService
  ) {};

  menuId: any = this.route.snapshot.paramMap.get("menu_id");

  menuItemData: any;
  dayMealListItemData: any;
  mealListItemData: any;
  dataLoaded = false;
  weekIndex: number = 0;

  dayLoaded = false;
  mealsLoaded = false;
  productsLoaded = false;

  ngOnInit(): void {
    this.getMenuDetails(this.menuId);
  }

  checkDataLoaded() {
    if (this.dayLoaded && this.mealsLoaded && this.productsLoaded) {
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
    this.getDayMealListDetails(weekMealId);
    this.getMealListDetails(weekMealId);
    this.getProducts();
  }

  swapWeek(newIndex: number) {
    this.weekIndex = newIndex;
    this.mealListItemData = [];
    this.refreshMealList();
  }

  deleteWeekMealOnIndex(index: number) {
    let weekMealId = this.menuItemData.weekMealList[index];

    this.weekMealService.deleteWeekMealById(weekMealId).subscribe(
      result => {
        this.notificationService.warn(":: Usunięto pomyślnie! ::");
        this.weekIndex = (index > 0) ? (index - 1) : 0;
        this.menuItemData = this.menuItemData.weekMealList.filter( id => {
          return id != weekMealId;
        });
        this.mealListItemData = [];
        this.getMenuDetails(this.menuId);
      }, error => {
        this.notificationService.error(":: Wystąpił błąd podczas usuwania! ::");
        this.refreshMealList();
      });
  }

  refreshMealList() {
    let weekMealId = this.menuItemData.weekMealList[this.weekIndex];
    this.loadWeekData(weekMealId);
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

  getProducts() {
    this.productService.getProducts()
      .subscribe(
        (data: Product[] ) => {
          this.productService.productList = [...data];
          this.productsLoaded = true;
          this.checkDataLoaded();
        });
  }

  translateDayType(name) {
    return translateDayType(name);
  }

  getFoodPropertiesWeekSummary(dishType: string, property: string, withLimits: boolean) {
    if (this.mealListItemData == null)
      return this.setLimitStatus(0, property, withLimits);

    let meals = this.mealListItemData;

    if (!withLimits) {
      meals = meals.filter( meal => {
        return meal.foodType == dishType;
      });
    }

    let value = this.getFoodProperties(meals, property);

    return this.setLimitStatus(value/7, property, withLimits);;
  }

  getFoodPropertiesDaySummary(day: DayMeal, property: string) {
    if (day.mealList == null)
      return this.setLimitStatus(0, property, true);

    let dayMeals = day.mealList;
    let meals = this.mealListItemData.filter( meal => {
      return dayMeals.includes(meal.id);
    });

    let value = this.getFoodProperties(meals, property);
    return this.setLimitStatus(value, property, true);
  }

  getFoodProperties(meals: Meal[], property: string) {

    let value = 0;
    for (let meal of meals) {
      let isProduct = (meal.isProduct == 1);

      for (let product of meal.productList) {
        let grams = product.grams;

        let originProduct = this.productService.productList.find(p => {
          return p.id == product.productId;
        });
        if (originProduct == null) continue;

        let foodProperties = originProduct.foodProperties;

        let productValue = (foodProperties[property] * grams) / 100;

        value += productValue;
      }
    }

    return value;
  }

  setLimitStatus(value: number, property: string, withLimits: boolean) {
    if (this.menuItemData.id == null) return "";
    let limit = 0;
    let strValue = "";

    if (property == "energyValue") {
      limit = this.menuItemData.energyLimit;
      strValue = "Kcal: ";
    } else if (property == "proteins") {
      limit = this.menuItemData.proteinsLimit;
      strValue = "B: ";
    } else if (property == "fats") {
      limit = this.menuItemData.fatsLimit;
      strValue = "T: ";
    } else if (property == "carbohydrates") {
      limit = this.menuItemData.carbohydratesLimit;
      strValue = "W: ";
    }

    if (withLimits) {
      return strValue + value.toFixed(2) + "/" + limit.toFixed(2);
    } else {
      return strValue + value.toFixed(2);
    }

  }

}
