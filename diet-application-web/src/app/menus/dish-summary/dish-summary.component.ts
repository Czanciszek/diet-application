import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {MealService} from "../../service/meal.service";
import {Meal} from "../../model/meal";
import {DayMeal} from "../../model/day-meal";
import {FormArray} from "@angular/forms";
import {MealAddComponent} from "../meal-add/meal-add.component";
import {MatDialog} from "@angular/material/dialog";

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
  @Output()
  refreshItems = new EventEmitter<boolean>();

  constructor(
    private dialog: MatDialog,
    private service: MealService,
  ) { }

  ngOnInit(): void {
  }

  onEdit(meal) {
    for (const product of meal.productForDishList) {
      (<FormArray>this.service.form.get('productForDishList')).push(this.service.addProductFormGroup());
    }
    this.service.populateForm(meal);
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(MealAddComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.refreshItems.emit();
    });
  }

  onDeleteMealButtonClick(mealId) {
    this.service.deleteMeal(mealId).subscribe( () => {
      this.refreshItems.emit();
    });
  }

  copyMeal(meal, value) {
    let dayIndex = Object.keys(this.daysList).find( key => this.daysList[key].dayType === value);
    const copyMeal = Object.assign({}, meal);
    copyMeal.dayMealId = this.daysList[dayIndex].id;
    this.service.copyMeal(copyMeal).subscribe( () => {
      this.refreshItems.emit();
    });
  }
}
