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
    private service: DishService,
    private productService: ProductService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<DishComponent>
  ) { }

  amountTypes = AMOUNT_TYPES;
  foodTypes = FOOD_TYPES;

  ngOnInit(): void {
    if (this.service.form.get('id').value) {
      let dishId = this.service.form.get('id').value;
      this.getProductsDetails(dishId);
    }

    this.productService.getProducts()
      .subscribe(
        (data: Product[] ) => {
          this.productService.productList = [...data];
      });
  }

  onClear() {
    (<FormArray>this.service.form.get('products')).clear();
    this.service.form.reset();
  }

  getProductsDetails(dishId) {
    this.productService.getProductsByDishId(dishId)
      .subscribe(
        (productsData: Dish[]) => {
          let products = this.service.form.value.products;
          for (let i = 0; i < products.length; i++) {
            for (let product of productsData) {
              if (product.id == products[i].productId) {
                (<HTMLInputElement>document.getElementById("name"+i)).value = productsData[i].name;
              }
            }
          }
        });
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
        products.at(productIndex).get('productId').patchValue(result.id);
        products.at(productIndex).get('productName').patchValue(result.name);
        this.service.form.patchValue({
          products: [products]
        });
      }
    });
  }

  getProductSummary(index) {
    let product = this.service.form.get('products').value[index];
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
