import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {MealService} from "../../service/meal.service";
import {Meal} from "../../model/meal";
import {DayMeal} from "../../model/day-meal";
import {FormArray} from "@angular/forms";
import {MealAddComponent} from "../meal-add/meal-add.component";
import {MatDialog} from "@angular/material/dialog";
import {ProductService} from "../../service/product.service";

@Component({
  selector: 'app-dish-summary',
  templateUrl: './dish-summary.component.html',
  styleUrls: ['./dish-summary.component.css']
})
export class DishSummaryComponent implements OnInit {

  @Input()
  menuId: any;
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
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.getFoodProperties();
  }

  onEdit(meal) {
    for (const product of meal.productList) {
      (<FormArray>this.service.form.get('productList')).push(this.service.addProductFormGroup());
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

    dialogRef.componentInstance.menuId = this.menuId;

    dialogRef.afterClosed().subscribe(result => {
      this.refreshItems.emit();
    });
  }

  onDeleteMealButtonClick(mealId) {
    this.service.deleteMeal(mealId).subscribe(() => {
      this.refreshItems.emit();
    });
  }

  copyMeal(meal, value) {
    let dayIndex = Object.keys(this.daysList).find(key => this.daysList[key].dayType === value);
    const copyMeal = Object.assign({}, meal);
    copyMeal.dayMealId = this.daysList[dayIndex].id;
    this.service.copyMeal(copyMeal).subscribe(() => {
      this.refreshItems.emit();
    });
  }

  getFoodProperties() {
    let grams = 0;
    let energy = 0;
    let proteins = 0;
    let fats = 0;
    let carbohydrates = 0;

    let products = this.mealItem.productList;
    let isProduct = (this.mealItem.isProduct == 1);
    for (let product of products) {
      grams = product.grams;

      if (this.productService.menuProductMap[product.productId] != null) {
        let foodProperties = this.productService.menuProductMap[product.productId].foodProperties;

        energy += (foodProperties.energyValue * grams) / 100;
        proteins += (foodProperties.proteins * grams) / 100;
        fats += (foodProperties.fats * grams) / 100;
        carbohydrates += (foodProperties.carbohydrates * grams) / 100;
      }
    }

    if (!isProduct) {
      let dishPortions = this.mealItem.dishPortions;
      let portions = this.mealItem.portions;
      let proportions = portions / dishPortions;

      energy *= proportions;
      proteins *= proportions;
      fats *= proportions;
      carbohydrates *= proportions;
    }

    this.foodPropertiesSummary = "Kcal: " + energy.toFixed(2) +
      "    B: " + proteins.toFixed(2) +
      "    T: " + fats.toFixed(2) +
      "    W: " + carbohydrates.toFixed(2);
  }
}
