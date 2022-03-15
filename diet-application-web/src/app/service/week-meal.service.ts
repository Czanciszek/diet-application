import { Injectable } from '@angular/core';
import {RestapiService} from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class WeekMealService {

  constructor(
    private restApiService: RestapiService
  ) { }

  getWeekMealById(weekMealId) {
    return this.restApiService.get("weekmeals/" + weekMealId);
  }
}
