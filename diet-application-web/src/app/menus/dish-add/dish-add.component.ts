import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { MealAddComponent } from "../meal-add/meal-add.component";
import { MealService } from "../../service/meal.service";

@Component({
    selector: 'app-dish-add',
    templateUrl: './dish-add.component.html',
    styleUrls: ['./dish-add.component.css'],
    standalone: false
})
export class DishAddComponent implements OnInit {

  constructor(
    private dialog: MatDialog,
    private mealSerivce: MealService
  ) { }

  @Input()
  menuId: any;
  @Input()
  dayId: string;
  @Input()
  dishType: string;
  @Input()
  menuItem: any;
  @Input()
  currentDate: string;

  @Output()
  refreshItems = new EventEmitter<boolean>();

  ngOnInit(): void {
  }

  openDialog() {
    this.mealSerivce.initializeFormGroup();
    this.mealSerivce.form.get('foodType').patchValue(this.dishType);

    let dialogRef = this.dialog.open(MealAddComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    let selectedDate = new Date(this.currentDate);
    selectedDate.setHours(0, 0, 0);
    let weekMeal = this.menuItem.weekMenuList.find(weekMenu => {
      const searchedWeekMenu = Object.keys(weekMenu.meals).find(day => {
        let date = new Date(day);
        date.setHours(0, 0, 0);
        return selectedDate.toDateString() === date.toDateString();
      });

      return searchedWeekMenu != null;
    });

    dialogRef.componentInstance.weekMealId = weekMeal.id;
    dialogRef.componentInstance.menuId = this.menuId;
    dialogRef.componentInstance.patientId = this.menuItem.patient.id;
    dialogRef.componentInstance.currentDate = this.currentDate;


    dialogRef.afterClosed().subscribe(() => {
      this.refreshItems.emit();
    });
  }
}
