import { Injectable } from '@angular/core';
import {RestapiService} from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class WeekMealService {

  constructor(
    private restApiService: RestapiService
  ) { }

  deleteWeekMealById(weekMealId) {
    return this.restApiService.delete("weekmeals/" + weekMealId);
  }
}
