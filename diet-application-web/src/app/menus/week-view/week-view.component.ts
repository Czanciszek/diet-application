import { Component, OnInit } from '@angular/core';
import { MenuService } from "../../service/menu.service";
import { ActivatedRoute } from "@angular/router";
import { WeekMeal } from "../../model/week-meal";
import { NotificationService } from "../../service/notification.service";
import { translateDayType } from "../../material/helper/polish-translate";
import { Meal } from "../../model/meal";
import { Product } from "../../model/product";
import { ProductService } from "../../service/product.service";

@Component({
  selector: 'week-view.component',
  styleUrls: ['week-view.component.css'],
  templateUrl: 'week-view.component.html',
})
export class WeekViewComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private menuService: MenuService,
    private productService: ProductService
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

        console.log("DATA", data);

        this.menuItemData = data;

        for (let week of data.weekMenuList) {
          this.weekMealsData.push(week.meals);
        }

        this.updateWeekIndex(this.weekIndex);
        this.dataLoaded = true;
      }
      );
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

  getProducts() {
    this.productService.getProducts()
      .subscribe((data: Product[]) => {
        this.productService.productList = [...data];
      }
      );
  }

  swapWeek(newIndex: number) {
    this.updateWeekIndex(newIndex);
  }

  deleteWeekMealOnIndex(index: number) {
    console.log("// TODO: DELETE WEEK MEAL OF INDEX " + index);

    //     this.weekMealService.deleteWeekMealById(weekMealId).subscribe(
    //       result => {
    //         this.notificationService.warn(":: Usunięto pomyślnie! ::");
    //         this.weekIndex = (index > 0) ? (index - 1) : 0;
    //         this.menuItemData = this.menuItemData.weekMealList.filter( id => {
    //           return id != weekMealId;
    //         });
    //         this.mealListItemData = [];
    //         this.refreshMealList();
    //       }, error => {
    //         this.notificationService.error(":: Wystąpił błąd podczas usuwania! ::");
    //         this.refreshMealList();
    //       });
  }

  refreshMealList() {
    this.getMenuDetails();
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

  dateChanged(event, day: string) {
    let selectedDate = new Date(event.value);
    selectedDate.setHours(0, 0, 0);
    console.log("// TODO: Copy day to " + selectedDate + " from " + day);
    //       let dayMeal = this.menuItemData.dayMealTypeList.find(key => {
    //         let keyValue = new Date(key.date);
    //         keyValue.setHours(0,0,0);
    //         return selectedDate.toDateString() === keyValue.toDateString();
    //       });
    //
    //       if (dayMeal != null && dayMeal.id != null) {
    //         this.copyDayMeal(dayMeal, originDayMealId);
    //       }
  }

  //   copyDayMeal(desiredDate: string, originDay: string) {
  //     this.dayMealService.copyDayMeal(dayMeal, originDayMealId).subscribe(() => {
  //       this.refreshMealList();
  //     });
  //   }

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

  clearDayMealButtonClick(day: string) {
    if (!confirm("Na pewno chcesz wyczyścić ten dzień?")) {
      return;
    }

    console.log("// TODO: CLEAR DATE NAMED " + day);
    //      this.dayMealService.clearDayMeal(dayMealId).subscribe(() => {
    //        this.refreshMealList();
    //      });
  }
}
