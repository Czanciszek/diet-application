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

  foodPropertiesSummary = "";

  constructor(
    private dialog: MatDialog,
    private service: MealService,
  ) { }

  ngOnInit(): void {
    this.getFoodProperties();
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

  getFoodProperties() {
    let grams = 0;
    let energy = 0;
    let proteins = 0;
    let fats = 0;
    let carbohydrates = 0;

    let products = this.mealItem.productForDishList;
    let isProduct = (this.mealItem.isProduct == 1);
    for (let product of products) {
      if (isProduct) {
        grams = this.mealItem.grams;
      } else {
        grams = product.grams;
      }

      energy += (product.foodProperties.energyValue * grams) / 100;
      proteins += (product.foodProperties.proteins * grams) / 100;
      fats += (product.foodProperties.fats * grams) / 100;
      carbohydrates += (product.foodProperties.carbohydrates * grams) / 100;
    }

    if (!isProduct) {
      let portions = this.mealItem.portions;
      energy /= portions;
      proteins /= portions;
      fats /= portions;
      carbohydrates /= portions;
    }

      this.foodPropertiesSummary = "Kcal: " + energy.toFixed(2) +
        "    B: " + proteins.toFixed(2) +
        "    T: " + fats.toFixed(2) +
        "    W: " + carbohydrates.toFixed(2);
  }
}
