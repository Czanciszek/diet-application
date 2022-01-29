import {Component, OnInit, ViewChild} from '@angular/core';
import {DishService} from "../../service/dish.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {Dish} from "../../model/dish";

@Component({
  selector: 'app-dish-select',
  templateUrl: './dish-select.component.html',
  styleUrls: ['./dish-select.component.css']
})
export class DishSelectComponent implements OnInit {

  constructor(
    private service: DishService,
    public dialogRef: MatDialogRef<DishSelectComponent>,
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.fetchData();
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  fetchData() {
    this.getDishList();
  }

  getDishList() {
    this.service.getDishes().subscribe(
      (data: Dish[]) => {
        this.fetchResults(data);
      });
  }

  fetchResults(data: Dish[]) {
    this.listData = new MatTableDataSource([...data]);
    this.listData.sort = this.sort;
    this.listData.paginator = this.paginator;
  }

  onSelect(dish) {
    this.dialogRef.close(dish);
  }

  onClose() {
    this.dialogRef.close();
  }

}
