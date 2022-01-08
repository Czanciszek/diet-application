import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-week-header',
  templateUrl: './week-header.component.html',
  styleUrls: ['./week-header.component.css']
})
export class WeekHeaderComponent implements OnInit {

  constructor() { }

  @Input()
  weekIndex: number;
  @Input()
  maxWeekIndex: number;

  @Output()
  weekIndexChanged = new EventEmitter<number>();

  ngOnInit(): void {
  }

  swapWeek(next: boolean) {
    let newIndex = 0;

    if (next && (this.maxWeekIndex > (this.weekIndex + 1))) {
      newIndex = this.weekIndex + 1;
    } else if (!next && (this.weekIndex > 0)) {
      newIndex = this.weekIndex - 1;
    } else {
      return;
    }

    this.weekIndexChanged.emit(newIndex);
  }
}
