import {Component, Input, OnInit} from '@angular/core';

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

}
