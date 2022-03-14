import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";

@Injectable({
  providedIn: 'root'
})
export class WeekMealService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  getWeekMealById(weekMealId) {
    return this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "weekmeals/" + weekMealId, this.httpOptions);
  }

  deleteWeekMealById(weekMealId) {
    return this.http.delete(GlobalVariable.SERVER_ADDRESS +
          GlobalVariable.DATABASE_SERVICE +
          "weekmeals/" + weekMealId, this.httpOptions);
  }
}
