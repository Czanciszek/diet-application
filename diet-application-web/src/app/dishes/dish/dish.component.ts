import {Component, OnInit} from '@angular/core';
import {NotificationService} from "../../service/notification.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DishService} from "../../service/dish.service";
import {FormArray} from "@angular/forms";
import {ProductSelectComponent} from "../../products/product-select/product-select.component";
import {ProductService} from "../../service/product.service";
import {Dish} from "../../model/dish";
import {Product} from "../../model/product";
import {FOOD_TYPES} from "../../model/helpers/foodTypes";
import {AMOUNT_TYPES} from "../../model/helpers/amountTypes";

@Component({
  selector: 'app-dish',
  templateUrl: './dish.component.html',
  styleUrls: ['./dish.component.css']
})
export class DishComponent implements OnInit {

  constructor(
    private dishService: DishService,
    private productService: ProductService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<DishComponent>
  ) { }

  dishForm = this.dishService.form;

  amountTypes = AMOUNT_TYPES;
  foodTypes = FOOD_TYPES;

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts()
      .subscribe(
        (data: Product[] ) => {
        this.productService.productList = [...data];
      });
  }

  onClear() {
    (<FormArray>this.dishForm.get('products')).clear();
    this.dishService.form.reset();
  }

  onSubmit() {
    if (this.dishForm.valid) {
      this.trimDishName();

      if (!this.dishForm.get('id').value) {
        this.dishService.insertDish(this.dishService.form.value).subscribe();
        this.notificationService.success(":: Potrawa stworzona pomyślnie! ::");
      } else {
        this.dishService.updateDish(this.dishService.form.value).subscribe();
        this.notificationService.success(":: Potrawa zaktualizowana pomyślnie! ::");
      }
      this.onClose();
    }
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  addProductButtonClick() {
    (<FormArray>this.dishForm.get('products')).push(this.dishService.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.dishForm.get('products')).removeAt(productIndex);
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
        let products = (<FormArray>this.dishService.form.get('products'));
        products.at(productIndex).get('productId').patchValue(result.id);
        products.at(productIndex).get('productName').patchValue(result.name);
        this.dishService.form.patchValue({
          products: [products]
        });
      }
    });
  }

  trimDishName() {
    let dishName = this.dishForm.get('name').value.trim();
    this.dishForm.get('name').patchValue(dishName);
  }

  getProductSummary(index) {
    let product = this.dishForm.get('products').value[index];
    return this.calculateProperties([product]);
  }

  calculateProperties(products) {
      let energy = 0;
      let proteins = 0;
      let fats = 0;
      let carbohydrates = 0;

      for (let product of products) {
        if (product == null || this.productService.productList == null) {
          continue;
        }

        let grams = product.grams;

        let originProduct = this.productService.productList.find(p => {
          return p.id == product.productId;
        });

        if (originProduct == null) continue;
        let foodProperties = originProduct.foodProperties;

         energy += (foodProperties.energyValue * grams) / 100;
         proteins += (foodProperties.proteins * grams) / 100;
         fats += (foodProperties.fats * grams) / 100;
         carbohydrates += (foodProperties.carbohydrates * grams) / 100;
      }

      return "Kcal: " + energy.toFixed(2) +
        "    B: " + proteins.toFixed(2) +
        "    T: " + fats.toFixed(2) +
        "    W: " + carbohydrates.toFixed(2);
    }

}
