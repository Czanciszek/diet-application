import {Component} from '@angular/core';

@Component({
  selector: 'week-view.component',
  styleUrls: ['week-view.component.css'],
  templateUrl: 'week-view.component.html',
})
export class WeekViewComponent {

  days = [
    'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'
  ];

}
