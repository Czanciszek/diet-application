import {Component, Input, OnInit} from '@angular/core';
import {MealService} from "../../service/meal.service";
import {Meal} from "../../model/meal";
import {DayMeal} from "../../model/day-meal";

@Component({
  selector: 'app-dish-summary',
  templateUrl: './dish-summary.component.html',
  styleUrls: ['./dish-summary.component.css']
})
export class DishSummaryComponent implements OnInit {

  @Input()
  mealItem: Meal;
  @Input()
  daysList: DayMeal[];

  constructor(
    private service: MealService,
  ) { }

  ngOnInit(): void {
  }

  onDeleteMealButtonClick(mealId) {
    this.service.deleteMeal(mealId);
  }

  copyMeal(meal, value) {
    let dayIndex = Object.keys(this.daysList).find( key => this.daysList[key].dayType === value);
    const copyMeal = Object.assign({}, meal);
    console.log(copyMeal);
    copyMeal.dayMealId = this.daysList[dayIndex].id;
    this.service.copyMeal(copyMeal).subscribe();
  }
}
