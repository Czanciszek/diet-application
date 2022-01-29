import {Component, Input, OnInit, Output, EventEmitter, ViewChild, ElementRef} from '@angular/core';
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
  menuItemData: any;
  @Input()
  dayItem: any;
  @Input()
  mealItem: Meal;
  @Input()
  daysList: DayMeal[];
  @Output()
  refreshItems = new EventEmitter<boolean>();

  @ViewChild('elementToFocus') _input: ElementRef;

  foodPropertiesSummary = "";

  constructor(
    private dialog: MatDialog,
    private service: MealService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    this.getFoodProperties();
  }

  onEdit() {
    for (const product of this.mealItem.productList) {
      (<FormArray>this.service.form.get('productList')).push(this.service.addProductFormGroup());
    }
    this.service.populateForm(this.mealItem);
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(MealAddComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refreshItems.emit();
    });
  }

  onDeleteMealButtonClick(mealId) {
    this.service.deleteMeal(mealId).subscribe(() => {
      this.refreshItems.emit();
    });
  }

  copyMeal(dayMeal) {
    const copyMeal = Object.assign({}, this.mealItem);
    copyMeal.dayMealId = dayMeal.id;
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

    this.foodPropertiesSummary = "Kcal: " + energy.toFixed(2) +
      "    B: " + proteins.toFixed(2) +
      "    T: " + fats.toFixed(2) +
      "    W: " + carbohydrates.toFixed(2);
  }

  _openCalendar(picker) {
      picker.open();
      setTimeout(
        () => this._input.nativeElement.focus()
      );
    }

  dateChanged(event) {
    let selectedDate = new Date(event.value);
    selectedDate.setHours(0,0,0);

    let dayMeal = this.menuItemData.dayMealTypeList.find(key => {
      let keyValue = new Date(key.date);
      keyValue.setHours(0,0,0);
      return selectedDate.toDateString() === keyValue.toDateString();
    });

    if (dayMeal != null && dayMeal.id != null) {
      this.copyMeal(dayMeal);
    }
  }

}
