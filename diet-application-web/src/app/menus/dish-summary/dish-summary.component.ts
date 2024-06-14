import { Component, Input, OnInit, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { MealService } from "../../service/meal.service";
import { Meal } from "../../model/meal";
import { MealAddComponent } from "../meal-add/meal-add.component";
import { MatDialog } from "@angular/material/dialog";
import { ProductService } from "../../service/product.service";

@Component({
  selector: 'app-dish-summary',
  templateUrl: './dish-summary.component.html',
  styleUrls: ['./dish-summary.component.css']
})
export class DishSummaryComponent implements OnInit {

  @Input()
  menuItemData: any;
  @Input()
  date: any;
  @Input()
  mealItem: Meal;

  @Output() refreshItems = new EventEmitter<boolean>();
  @Output() deleteMeal = new EventEmitter<string>();

  @ViewChild('elementToFocus') _input: ElementRef;

  foodPropertiesSummary = "";

  isProductAttachedToRecipes = false;

  constructor(
    private dialog: MatDialog,
    private mealService: MealService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.getFoodProperties();
    this.checkMealAttachedToRecipes();
  }

  onEdit() {
    this.mealService.populateForm(this.mealItem);
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(MealAddComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    let selectedDate = new Date(this.date);
    selectedDate.setHours(0, 0, 0);
    let weekMeal = this.menuItemData.weekMenuList.find(weekMenu => {
      const searchedWeekMenu = Object.keys(weekMenu.meals).find(day => {
        let date = new Date(day);
        date.setHours(0, 0, 0);
        return selectedDate.toDateString() === date.toDateString();
      });

      return searchedWeekMenu != null;
    });

    dialogRef.componentInstance.weekMealId = weekMeal.id;
    dialogRef.componentInstance.patientId = this.menuItemData.patient.id;

    dialogRef.afterClosed().subscribe(result => {
      this.refreshItems.emit();
    });
  }

  onDeleteMealButtonClick(mealId: string) {
    if (!confirm("Na pewno chcesz usunąć tę potrawę?")) {
      return;
    }

    this.deleteMeal.emit(mealId);
  }

  getFoodProperties() {
    let grams = 0;
    let energy = 0;
    let proteins = 0;
    let fats = 0;
    let carbohydrates = 0;

    let products = this.mealItem.productList;
    for (let product of products) {
      grams = product.grams;

      let originProduct = this.productService.productList.find(p => {
        return p.id == product.productId;
      });
      if (originProduct == null) continue;

      let foodProperties = originProduct.foodProperties;

      energy += (foodProperties.energyValue * grams) / 100;
      proteins += (foodProperties.proteins * grams) / 100;
      fats += (foodProperties.fats * grams) / 100;
      carbohydrates += (foodProperties.carbohydrates * grams) / 100;
    }

    this.foodPropertiesSummary = "Kcal: " + energy.toFixed(2) +
      "    B: " + proteins.toFixed(2) +
      "    T: " + fats.toFixed(2) +
      "    W: " + carbohydrates.toFixed(2);
  }

  checkMealAttachedToRecipes() {
    this.isProductAttachedToRecipes = this.mealItem.attachToRecipes && !this.mealItem.isProduct
  }

  copyMealDateSelected(event) {
    let selectedDate = new Date(event.value);
    selectedDate.setHours(0, 0, 0);

    let selectedDay: string = ""
    let weekMeal = this.menuItemData.weekMenuList.find(weekMenu => {

      const searchedWeekMenu = Object.keys(weekMenu.meals).find(day => {
        let date = new Date(day);
        date.setHours(0, 0, 0);
        if (selectedDate.toDateString() === date.toDateString()) {
          selectedDay = day;
          return true;
        }
        return false;
      });

      return selectedDay && searchedWeekMenu != null;
    });

    this.mealService.copyMeal(this.menuItemData.id, selectedDay, this.mealItem.id).subscribe(() => {
      this.refreshItems.emit();
    });
  }

}
