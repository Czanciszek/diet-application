import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";

@Injectable({
  providedIn: 'root'
})
export class DayMealService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  getDayMealListByListId(dayMealIdList) {
    return this.http.get(GlobalVariable.SERVER_ADDRESS +
      GlobalVariable.DATABASE_SERVICE + "daymeals/list/" + dayMealIdList, this.httpOptions);
  }
}
