import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-dish-selector',
  templateUrl: './dish-selector.component.html',
  styleUrls: ['./dish-selector.component.css']
})
export class DishSelectorComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  menuId: number;

  onSelectDishesButtonClick() {
    console.log("aaa", this.menuId);
  }

}
