import { Injectable } from '@angular/core';
import {RestapiService} from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class WeekMealService {

  constructor(
    private restApiService: RestapiService
  ) { }

  getWeekMealsByMenuId(menuId) {
    return this.restApiService.get("weekmeals/menu/" + menuId, "v2");
  }

  deleteWeekMealById(weekMealId) {
    return this.restApiService.delete("weekmeals/" + weekMealId);
  }
}
