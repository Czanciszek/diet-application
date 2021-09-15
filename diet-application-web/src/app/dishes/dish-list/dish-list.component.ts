import {Component, OnInit, ViewChild} from '@angular/core';
import {DishService} from "../../service/dish.service";
import {MatDialog} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {DishComponent} from "../dish/dish.component";
import {FormArray} from "@angular/forms";

@Component({
  selector: 'app-dish-list',
  templateUrl: './dish-list.component.html',
  styleUrls: ['./dish-list.component.css']
})
export class DishListComponent implements OnInit {

  constructor(
    private service: DishService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'portions', 'foodType', 'actions'];

  foodTypes = [
    { id: "BREAKFEST", value: "Śniadanie" },
    { id: "BRUNCH", value: "II śniadanie" },
    { id: "DINNER", value: "Obiad" },
    { id: "TEA", value: "Podwieczorek"},
    { id: "SUPPER", value: "Kolacja" },
    { id: "PRE_WORKOUT", value: "Przedtreningówka" },
    { id: "POST_WORKOUT", value: "Potreningówka"},
  ];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.service.getDishes().subscribe(
      list => {
        let array = list.map(item => {
          return {
            id: item.id,
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

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onCreate() {
    (<FormArray>this.service.form.get('products')).push(this.service.addProductFormGroup());
    this.service.initializeFormGroup();
    this.openDialog();
  }

  onEdit(dish) {
    for (const product of dish.products) {
      (<FormArray>this.service.form.get('products')).push(this.service.addProductFormGroup());
    }
    this.service.populateForm(dish);
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(DishComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.ngOnInit();
    });
  }

  onDelete(dishId) {
    if (confirm("Are you sure to delete this dish?")) {
      this.service.deleteDish(dishId);
      this.notificationService.warn(":: Deleted succesfully! ::");
      this.ngOnInit();
    }
  }

  getFoodType(foodTypeId) {
    let foodType = this.foodTypes.filter(x => x.id == foodTypeId);
    return foodType[0].value;
  }
}
