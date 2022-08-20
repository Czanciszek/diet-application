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
import {Product} from "../../model/product";
import {FOOD_TYPES} from "../../model/helpers/foodTypes";
import {AMOUNT_TYPES} from "../../model/helpers/amountTypes";

@Component({
  selector: 'app-meal-add',
  templateUrl: './meal-add.component.html',
  styleUrls: ['./meal-add.component.css']
})
export class MealAddComponent implements OnInit {

  constructor(
    private mealService: MealService,
    private menuService: MenuService,
    private productService: ProductService,
    private dishService: DishService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<MealAddComponent>
  ) { }

  mealServiceForm = this.mealService.form;

  public menuId: any;

  amountTypes = AMOUNT_TYPES;
  foodTypes = FOOD_TYPES;

  withPortions = true;
  blockProduct = false;
  blockDish = false;
  tabIndex = 0;
  portionCurrentValue = 0;

  ngOnInit(): void {
    let products = (<FormArray>this.mealService.form.get('productList'));
    if (this.mealService.form.get('id').value != null) {
      this.checkPortionOption();
      this.tabIndex = this.mealService.form.get('isProduct').value;
      if (this.tabIndex == 1) {
        this.blockDish = true;
        let productGrams = products.at(0).get('grams').value;
        setTimeout( () => {
          (<HTMLInputElement>document.getElementById("product_grams")).value = productGrams;
        });
      } else {
        this.blockProduct = true;
        this.portionCurrentValue = this.mealService.form.get("portions").value;
      }
    }

  }

  onClear() {
    (<FormArray>this.mealService.form.get('productList')).clear();
    this.mealService.form.reset();
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.mealService.form.valid) {
      if (!this.mealService.form.get('id').value) {
        this.mealService.insertMeal(this.mealService.form.value).subscribe( result => {
          this.notificationService.success(":: Meal created successfully! ::");
          this.onClose();
        });
      } else {
        this.mealService.updateMeal(this.mealService.form.value).subscribe( result => {
          this.notificationService.success(":: Meal updated successfully! ::");
          this.onClose();
        });
      }
    }
  }

  checkPortionOption() {
   this.withPortions = this.mealService.form.get('grams').value == 0;
  }

  addProductButtonClick() {
    (<FormArray>this.mealService.form.get('productList')).push(this.mealService.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.mealService.form.get('productList')).removeAt(productIndex);
  }

  selectProductForDish(index) {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {

        // Update value in Form Group
        let products = (<FormArray>this.mealService.form.get('productList'));
        products.at(index).get('productId').patchValue(result.id);
        products.at(index).get('productName').patchValue(result.name);
        this.mealService.form.patchValue({
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

        (<FormArray>this.mealService.form.get('productList')).clear();

        // Update value in Form Group
        this.mealService.form.get('name').patchValue(result.name);
        this.mealService.form.get('isProduct').patchValue(1);

        let productForm = this.mealService.addProductFormGroup();
        productForm.get('productId').patchValue(result.id);
        productForm.get('productName').patchValue(result.name);
        (<FormArray>this.mealService.form.get('productList')).push(productForm);

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
      if (result == null) { return; }

      (<FormArray>this.mealService.form.get('productList')).clear();

      // Update value in Form Group
      this.mealService.form.get('name').patchValue(result.name);
      this.mealService.form.get('recipe').patchValue(result.recipe);
      this.mealService.form.get('dishPortions').patchValue(result.portions);
      this.mealService.form.get('isProduct').patchValue(0);

      for (let product of result.products) {
        let productForm = this.mealService.addProductFormGroup();
        productForm.get('productId').patchValue(product.productId);

        let portions = this.mealService.form.get('portions').value;
        this.portionCurrentValue = portions;
        let dishPortions = this.mealService.form.get('dishPortions').value;
        let proportions = portions / dishPortions;
        let grams = (product.grams * proportions).toFixed(2);

        productForm.get('grams').patchValue(grams);
        productForm.get('productName').patchValue(product.productName);
        productForm.get('amount').patchValue(product.amount);
        productForm.get('amountType').patchValue(product.amountType);
        (<FormArray>this.mealService.form.get('productList')).push(productForm);
      }
    });
  }

  getProductSummary(index) {
    let product = this.mealService.form.get('productList').value[index];
    return this.calculateProperties([product]);
  }

  getMealSummary() {
    return this.calculateProperties(this.mealService.form.get('productList').value);
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

      let isProduct = (this.mealService.form.get('isProduct').value == 1);
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

  gramsChanged(grams) {
    this.mealService.form.get('productList').get('0').get('grams').patchValue(grams);
  }

  portionOptionChanged(event) {
    if (event.source.checked) {
      this.mealService.form.get('grams').patchValue(0);
    }
    this.withPortions = event.source.checked;
  }

  portionCountChanged(event) {
    let newPortionValue = event.value;
    let proportions = newPortionValue / this.portionCurrentValue;

    let productList = this.mealService.form.get('productList').value;
    let productIndex = 0;
    for (let product of productList) {
      if (product.grams == null) continue;
      let grams = (product.grams * proportions).toFixed(2);
      this.mealService.form.get('productList').get(productIndex + "").get('grams').patchValue(grams);
      productIndex += 1;
    }

    this.portionCurrentValue = newPortionValue;
  }

  addToRecipeList(mealId) {
    this.mealService.form.get('originMealId').patchValue(mealId);
  }

  deleteFromRecipeList() {
    this.mealService.form.get('originMealId').patchValue("");
  }

}
