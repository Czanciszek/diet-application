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
}
