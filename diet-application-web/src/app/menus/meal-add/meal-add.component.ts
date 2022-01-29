import { Component, OnInit } from '@angular/core';
import {MealService} from "../../service/meal.service";
import {DishService} from "../../service/dish.service";
import {NotificationService} from "../../service/notification.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ProductSelectComponent} from "../../products/product-select/product-select.component";
import {FormArray} from "@angular/forms";
import {MenuService} from "../../service/menu.service";
import {DishSelectComponent} from "../../dishes/dish-select/dish-select.component";
import {ProductService} from "../../service/product.service";
import {Meal} from "../../model/meal";
import {FOOD_TYPES} from "../../model/helpers/foodTypes";
import {AMOUNT_TYPES} from "../../model/helpers/amountTypes";

@Component({
  selector: 'app-meal-add',
  templateUrl: './meal-add.component.html',
  styleUrls: ['./meal-add.component.css']
})
export class MealAddComponent implements OnInit {

  constructor(
    private service: MealService,
    private menuService: MenuService,
    private productService: ProductService,
    private dishService: DishService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<MealAddComponent>
  ) { }

  public menuId: any;

  amountTypes = AMOUNT_TYPES;
  foodTypes = FOOD_TYPES;

  withPortions = true;
  blockProduct = false;
  blockDish = false;
  tabIndex = 0;
  portionCurrentValue = 0;

  ngOnInit(): void {
    let products = (<FormArray>this.service.form.get('productList'));
    if (this.service.form.get('id').value != null) {
      this.checkPortionOption();
      this.tabIndex = this.service.form.get('isProduct').value;
      if (this.tabIndex == 1) {
        this.blockDish = true;
        let productGrams = products.at(0).get('grams').value;
        setTimeout( () => {
          (<HTMLInputElement>document.getElementById("product_grams")).value = productGrams;
        });
      } else {
        this.blockProduct = true;
        this.portionCurrentValue = this.service.form.get("portions").value;
      }
    }

  }

  onClear() {
    (<FormArray>this.service.form.get('productList')).clear();
    this.service.form.reset();
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (!this.service.form.get('id').value) {
        this.service.insertMeal(this.service.form.value).subscribe( result => {
          this.notificationService.success(":: Meal created successfully! ::");
          this.onClose();
        });
      } else {
        this.service.updateMeal(this.service.form.value).subscribe( result => {
          this.notificationService.success(":: Meal updated successfully! ::");
          this.onClose();
        });
      }
    }
  }

  checkPortionOption() {
   this.withPortions = this.service.form.get('grams').value == 0;
  }

  addProductButtonClick() {
    (<FormArray>this.service.form.get('productList')).push(this.service.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.service.form.get('productList')).removeAt(productIndex);
  }

  selectProductForDish(index) {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {
        // Update value in HTML form
        this.productService.menuProductMap[result.id] = result;

        // Update value in Form Group
        let products = (<FormArray>this.service.form.get('productList'));
        products.at(index).get('productId').patchValue(result.id);
        products.at(index).get('productName').patchValue(result.name);
        this.service.form.patchValue({
          products: [products]
        });
      }
    });
  }

  selectProduct() {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {
        // Update value in HTML form
        this.productService.menuProductMap[result.id] = result;

        (<FormArray>this.service.form.get('productList')).clear();

        // Update value in Form Group
        this.service.form.get('name').patchValue(result.name);
        this.service.form.get('isProduct').patchValue(1);

        let productForm = this.service.addProductFormGroup();
        productForm.get('productId').patchValue(result.id);
        (<FormArray>this.service.form.get('productList')).push(productForm);

        let grams = (<HTMLInputElement>document.getElementById("product_grams")).value;
        if (grams == null || grams == "") {
          this.gramsChanged(100);
          (<HTMLInputElement>document.getElementById("product_grams")).value = "100";
        } else {
          this.gramsChanged(grams);
        }

      }
    });
  }

  selectDish() {

    let dialogRef = this.dialog.open(DishSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {
        (<FormArray>this.service.form.get('productList')).clear();

        // Update value in Form Group
        this.service.form.get('name').patchValue(result.name);
        this.service.form.get('recipe').patchValue(result.recipe);
        this.service.form.get('dishPortions').patchValue(result.portions);
        this.service.form.get('isProduct').patchValue(0);

        for (let product of result.products) {
          let productForm = this.service.addProductFormGroup();
          productForm.get('productId').patchValue(product.productId);

          let portions = this.service.form.get('portions').value;
          this.portionCurrentValue = portions;
          let dishPortions = this.service.form.get('dishPortions').value;
          let proportions = portions / dishPortions;
          let grams = (product.grams * proportions).toFixed(2);

          productForm.get('grams').patchValue(grams);
          productForm.get('productName').patchValue(product.productName);
          productForm.get('amount').patchValue(product.amount);
          productForm.get('amountType').patchValue(product.amountType);
          (<FormArray>this.service.form.get('productList')).push(productForm);
        }
      }

      this.productService.getMenuProducts(this.menuId)
        .subscribe(
          (data: {} ) => {
          this.productService.menuProductMap = {...data};
          this.ngOnInit();
        });
    });
  }

  getProductSummary(index) {
    let product = this.service.form.get('productList').value[index];
    if (product == null || this.productService.menuProductMap[product.productId] == null) {
      return "";
    }

    let isProduct = (this.service.form.get('isProduct').value == 1);
    let grams = product.grams;

    let foodProperties = this.productService.menuProductMap[product.productId].foodProperties;

    let energy = (foodProperties.energyValue * grams) / 100;
    let proteins = (foodProperties.proteins * grams) / 100;
    let fats = (foodProperties.fats * grams) / 100;
    let carbohydrates = (foodProperties.carbohydrates * grams) / 100;

    return "Kcal: " + energy.toFixed(2) +
      "    B: " + proteins.toFixed(2) +
      "    T: " + fats.toFixed(2) +
      "    W: " + carbohydrates.toFixed(2);
  }

  gramsChanged(grams) {
    this.service.form.get('productList').get('0').get('grams').patchValue(grams);
  }

  portionOptionChanged(event) {
    if (event.source.checked) {
      this.service.form.get('grams').patchValue(0);
    }
    this.withPortions = event.source.checked;
  }

  portionCountChanged(event) {
    let newPortionValue = event.value;
    let proportions = newPortionValue / this.portionCurrentValue;

    let productList = this.service.form.get('productList').value;
    let productIndex = 0;
    for (let product of productList) {
      if (product.grams == null) continue;
      let grams = (product.grams * proportions).toFixed(2);
      this.service.form.get('productList').get(productIndex + "").get('grams').patchValue(grams);
      productIndex += 1;
    }

    this.portionCurrentValue = newPortionValue;
  }

}
