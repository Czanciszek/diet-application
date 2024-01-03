import { Component, Input, OnInit } from '@angular/core';
import { translateDishType } from "../../material/helper/polish-translate";

@Component({
  selector: 'app-dish-type',
  templateUrl: './dish-type.component.html',
  styleUrls: ['./dish-type.component.css']
})
export class DishTypeComponent implements OnInit {

  @Input()
  typeName: string;

  constructor() { }

  ngOnInit(): void {
  }

  translateDishType(type) {
    return translateDishType(type);
  }
}
