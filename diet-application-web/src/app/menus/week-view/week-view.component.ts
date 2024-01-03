import { Component, OnInit } from '@angular/core';
import { MenuService } from "../../service/menu.service";
import { ActivatedRoute } from "@angular/router";
import { NotificationService } from "../../service/notification.service";
import { translateDayType } from "../../material/helper/polish-translate";
import { Meal } from "../../model/meal";
import { Product } from "../../model/product";
import { ProductService } from "../../service/product.service";
import { MealService } from '../../service/meal.service';

@Component({
  selector: 'week-view.component',
  styleUrls: ['week-view.component.css'],
  templateUrl: 'week-view.component.html',
})
export class WeekViewComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private menuService: MenuService,
    private productService: ProductService,
    private mealService: MealService
  ) { };

  menuId: any = this.route.snapshot.paramMap.get("menu_id");

  weekMealsData: Map<string, Meal[]>[] = [];
  daysData: string[] = []
  menuItemData: any;
  dataLoaded = false;
  weekIndex: number = 0;

  ngOnInit(): void {
    this.getMenuDetails();
    this.getProducts();
  }

  getMenuDetails() {
    this.menuService.getMenuById(this.menuId)
      .subscribe((data: any) => {

        this.weekMealsData = [];
        this.menuItemData = data;

        for (let week of data.weekMenuList) {
          this.weekMealsData.push(week.meals);
        }

        this.updateWeekIndex(this.weekIndex);
        this.dataLoaded = true;
      });
  }

  getProducts() {
    this.productService.getProducts()
      .subscribe((data: Product[]) => {
        this.productService.productList = [...data];
      }); 
  }

  updateWeekIndex(newIndex: number) {
    this.weekIndex = newIndex;
    this.daysData = Object.keys(this.weekMealsData[this.weekIndex]).sort(this.compareDayMealDates);
  }

  compareDayMealDates(a: string, b: string): number {
    const date1 = new Date(a);
    const date2 = new Date(b);
    return date1.getTime() - date2.getTime();
  }

  swapWeek(newIndex: number) {
    this.updateWeekIndex(newIndex);
  }

  getFoodPropertiesWeekSummary(dishType: string, property: string, withLimits: boolean) {
    const weekMeals = this.weekMealsData[this.weekIndex];
    let meals = Object.values(weekMeals).reduce((wm1, wm2) => wm1.concat(wm2), []);

    if (meals == null || meals.length == 0)
      return this.setLimitStatus(0, property, withLimits);

    if (!withLimits) {
      meals = meals.filter(meal => {
        return meal.foodType == dishType;
      });
    }

    let value = this.getFoodProperties(meals, property);
    return this.setLimitStatus(value / 7, property, withLimits);
  }

  getFoodPropertiesDaySummary(day: string, property: string) {
    if (day == null || day === "")
      return this.setLimitStatus(0, property, true);

    const meals = this.weekMealsData[this.weekIndex][day];
    let value = this.getFoodProperties(meals, property);
    return this.setLimitStatus(value, property, true);
  }

  getFoodProperties(meals: Meal[], property: string) {

    let value = 0;
    for (let meal of meals) {

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

  copyDayMealDateSelected(event, originDay: string) {
    let selectedDate = new Date(event.value);
    selectedDate.setHours(0, 0, 0);

    let selectedDay: string = ""
    let weekMeal = this.menuItemData.weekMenuList.find(weekMenu => {

      const searchedWeekMenu = Object.keys(weekMenu.meals).find(day => {
        let date = new Date(day);
        date.setHours(0, 0, 0);
        if (selectedDate.toDateString() === date.toDateString()) {
          selectedDay = day;
          return true;
        }
        return false;
      });

      return selectedDay && searchedWeekMenu != null;
    });

    this.mealService.copyDay(weekMeal.id, selectedDay, originDay).subscribe(() => {
      this.getMenuDetails();
    });

  }

  translateDayType(date) {
    let name = this.indexToDayType(new Date(date).getDay());
    let dayType = translateDayType(name);
    return dayType + "\n" + date;
  }

  indexToDayType(index: number) {
    switch (index) {
      case 0:
        return "SUNDAY"
      case 1:
        return "MONDAY"
      case 2:
        return "TUESDAY"
      case 3:
        return "WEDNESDAY"
      case 4:
        return "THURSDAY"
      case 5:
        return "FRIDAY"
      case 6:
        return "SATURDAY"
      default:
        return "";
    }
  }

  onDeleteMeal(mealId) {

    const weekMenuId = this.menuItemData.weekMenuList[this.weekIndex].id;

    this.mealService.removeMeal(weekMenuId, mealId).subscribe(() => {
      this.getMenuDetails();
    });
  }

  clearDayMealButtonClick(day: string) {
    if (!confirm("Na pewno chcesz wyczyścić ten dzień?")) {
      return;
    }

    const weekMenuId = this.menuItemData.weekMenuList[this.weekIndex].id;
    const date = day;

    this.mealService.clearDay(weekMenuId, date).subscribe(() => {
      this.getMenuDetails();
    });
  }
}
