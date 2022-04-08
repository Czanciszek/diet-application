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

  deleteWeekMealById(weekMealId) {
    return this.http.delete(GlobalVariable.SERVER_ADDRESS +
          GlobalVariable.DATABASE_SERVICE +
          "weekmeals/" + weekMealId, this.httpOptions);
  }
}
