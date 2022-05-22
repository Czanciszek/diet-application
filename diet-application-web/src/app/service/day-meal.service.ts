import { Injectable } from '@angular/core';
import { RestapiService } from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class DayMealService {

  constructor(
    private restApiService: RestapiService
  ) { }

  getDayMealListByWeekMealId(weekMealId) {
    return this.restApiService.get("daymeals/list/" + weekMealId);
  }

  copyDayMeal(dayMeal, originDayMealId) {
    return this.restApiService.post(dayMeal, "daymeals/copy/" + originDayMealId);
  }

  clearDayMeal(dayMealId) {
    return this.restApiService.post(dayMealId, "daymeals/clear");
  }
}
