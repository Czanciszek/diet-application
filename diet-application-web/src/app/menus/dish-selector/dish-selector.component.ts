import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {DishListComponent} from "../../dishes/dish-list/dish-list.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-dish-selector',
  templateUrl: './dish-selector.component.html',
  styleUrls: ['./dish-selector.component.css']
})
export class DishSelectorComponent implements OnInit {

  constructor(
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  @Input()
  menuId: number;

  onEditMenuDishesButtonClick() {
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(DishListComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.componentInstance.menuId = this.menuId;
  }

}
