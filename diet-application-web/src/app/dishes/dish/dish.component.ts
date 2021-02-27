import {Component, OnInit} from '@angular/core';
import {NotificationService} from "../../service/notification.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DishService} from "../../service/dish.service";
import {FormArray} from "@angular/forms";
import {ProductSelectComponent} from "../../products/product-select/product-select.component";

@Component({
  selector: 'app-dish',
  templateUrl: './dish.component.html',
  styleUrls: ['./dish.component.css']
})
export class DishComponent implements OnInit {

  constructor(
    private service: DishService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<DishComponent>
  ) { }

  amountTypes = [
    { id: "PIECE", value: "Sztuka" },
    { id: "SPOON", value: "Łyżka" },
    { id: "SCOOPS", value: "Miarka" },
    { id: "HANDFUL", value: "Garść" },
    { id: "SLICE", value: "Plaster" },
    { id: "LEAVES", value: "Listek" },
    { id: "GLASS", value: "Szklanka" },
  ];

  mealTypes = [
    { id: "BREAKFEST", value: "Śniadanie" },
    { id: "BRUNCH", value: "II śniadanie" },
    { id: "DINNER", value: "Obiad" },
    { id: "TEA", value: "Podwieczorek"},
    { id: "SUPPER", value: "Kolacja" },
    { id: "PRE_WORKOUT", value: "Przedtreningówka" },
    { id: "POST_WORKOUT", value: "Potreningówka"},
  ];

  ngOnInit(): void {
  }

  onClear() {
    (<FormArray>this.service.form.get('products')).clear();
    this.service.form.reset();
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (!this.service.form.get('id').value) {
        this.service.insertDish(this.service.form.value).subscribe();
        this.notificationService.success(":: Dish created successfully! ::");
      } else {
        this.service.updateDish(this.service.form.value).subscribe();
        this.notificationService.success(":: Dish updated successfully! ::");
      }
      this.onClose();
    }
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  addProductButtonClick() {
    (<FormArray>this.service.form.get('products')).push(this.service.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.service.form.get('products')).removeAt(productIndex);
  }

  selectProduct(productIndex) {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {

        // Update value in HTML form
        (<HTMLInputElement>document.getElementById("name"+productIndex)).value = result.name;

        // Update value in Form Group
        let products = (<FormArray>this.service.form.get('products'));
        products.at(productIndex).value.product.id = result.id;
        products.at(productIndex).value.product.name = result.name;
        products.at(productIndex).value.product.type = result.type;
        products.at(productIndex).value.product.primaryImageId = result.primaryImageId;
        this.service.form.patchValue({
          products: [products]
        });
      }
    });
  }

}
