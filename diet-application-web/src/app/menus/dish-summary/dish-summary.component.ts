import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-dish-summary',
  templateUrl: './dish-summary.component.html',
  styleUrls: ['./dish-summary.component.css']
})
export class DishSummaryComponent implements OnInit {

  @Input()
  dishName: string;

  constructor() { }

  ngOnInit(): void {
  }

}
