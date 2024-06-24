import { Injectable } from '@angular/core';
import { Sort } from "@angular/material/sort";

@Injectable({
  providedIn: 'root'
})
export class DishSelectorService {

  constructor() { }

  lastInputString: string = "";
  selectedPageIndex: number = 0;
  selectedSort: Sort;

}
