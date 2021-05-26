import {Component, OnInit, ViewChild} from '@angular/core';
import {DishService} from "../../service/dish.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-dish-select',
  templateUrl: './dish-select.component.html',
  styleUrls: ['./dish-select.component.css']
})
export class DishSelectComponent implements OnInit {

  constructor(
    private service: DishService,
    private dialog: MatDialog,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<DishSelectComponent>,
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'actions'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit(): void {
    this.service.getDishes().subscribe(
      list => {
        let array = list.map(item => {
          return {
            id: item.id,
            header: item.header,
            primaryImageId: item.primaryImageId,
            type: item.type,
            name: item.name,
            products: item.products,
            foodType: item.foodType,
            portions: item.portions,
            recipe: item.recipe
          };
        });
        this.listData = new MatTableDataSource(array);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      });
  }

  onSelect(dish) {
    this.dialogRef.close(dish);
  }

  onDelete(dishId) {
    if (confirm("Are you sure to delete this dish?")) {
      this.service.deleteDish(dishId);
      this.notificationService.warn(":: Deleted succesfully! ::");
      this.ngOnInit();
    }
  }

  onClose() {
    this.dialogRef.close();
  }

}
