import { Component, OnInit } from '@angular/core';
import {MealService} from "../../service/meal.service";
import {NotificationService} from "../../service/notification.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ProductSelectComponent} from "../../products/product-select/product-select.component";
import {FormArray} from "@angular/forms";
import {MenuService} from "../../service/menu.service";
import {DishSelectComponent} from "../../dishes/dish-select/dish-select.component";

@Component({
  selector: 'app-meal-add',
  templateUrl: './meal-add.component.html',
  styleUrls: ['./meal-add.component.css']
})
export class MealAddComponent implements OnInit {

  constructor(
    private service: MealService,
    private menuService: MenuService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<MealAddComponent>
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

  blockProduct = false;
  blockDish = false;
  tabIndex = 0;

  ngOnInit(): void {
    if (this.service.form.get('id').value != null) {
      this.tabIndex = this.service.form.get('isProduct').value;
      if (this.tabIndex == 1) {
        this.blockDish = true;
      } else {
        this.blockProduct = true;
      }
    }
  }

  onClear() {
    (<FormArray>this.service.form.get('productForDishList')).clear();
    this.service.form.reset();
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (!this.service.form.get('id').value) {
        this.service.insertMeal(this.service.form.value).subscribe();
        this.notificationService.success(":: Meal created successfully! ::");
      } else {
        this.service.updateMeal(this.service.form.value).subscribe();
        this.notificationService.success(":: Meal updated successfully! ::");
      }
      this.onClose();
    }
  }

  addProductButtonClick() {
    (<FormArray>this.service.form.get('productForDishList')).push(this.service.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.service.form.get('productForDishList')).removeAt(productIndex);
  }

  selectProductForDish(index) {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {
        // Update value in HTML form
        (<HTMLInputElement>document.getElementById("name"+index)).value = result.name;

        // Update value in Form Group
        let products = (<FormArray>this.service.form.get('productForDishList'));
        products.at(index).value.product.id = result.id;
        products.at(index).value.product.name = result.name;
        products.at(index).value.product.type = result.type;
        products.at(index).value.product.primaryImageId = result.primaryImageId;
        products.at(index).get('foodProperties').patchValue(result.foodProperties);
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
        (<FormArray>this.service.form.get('productForDishList')).clear();

        // Update value in Form Group
        this.service.form.get('name').patchValue(result.name);
        this.service.form.get('isProduct').patchValue(1);

        if (this.service.form.get('grams').value == null) {
          this.service.form.get('grams').patchValue(100);
        }
        let productForm = this.service.addProductFormGroup();
        productForm.get('product').get('id').patchValue(result.id);
        productForm.get('product').get('name').patchValue(result.name);
        productForm.get('foodProperties').patchValue(result.foodProperties);
        (<FormArray>this.service.form.get('productForDishList')).push(productForm);
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
        (<FormArray>this.service.form.get('productForDishList')).clear();

        // Update value in Form Group
        this.service.form.get('name').patchValue(result.name);
        this.service.form.get('recipe').patchValue(result.recipe);
        this.service.form.get('isProduct').patchValue(0);

        for (let product of result.products) {
          let productForm = this.service.addProductFormGroup();
          productForm.get('product').get('id').patchValue(product.product.id);
          productForm.get('product').get('name').patchValue(product.product.name);
          productForm.get('grams').patchValue(product.grams);
          productForm.get('amount').patchValue(product.amount);
          productForm.get('amountType').patchValue(product.amountType);
          productForm.get('foodProperties').patchValue(product.foodProperties);
          (<FormArray>this.service.form.get('productForDishList')).push(productForm);
        }
      }
    });
  }


  getProductSummary(index) {
    let product = this.service.form.get('productForDishList').value[index];
    if (product != null && product.foodProperties != null) {
      let isProduct = (this.service.form.get('isProduct').value == 1);
      let grams;
      if (isProduct) {
        grams = this.service.form.get('grams').value;
      } else {
        grams = product.grams;
      }

      let energy = (product.foodProperties.energyValue * grams) / 100;
      let proteins = (product.foodProperties.proteins * grams) / 100;
      let fats = (product.foodProperties.fats * grams) / 100;
      let carbohydrates = (product.foodProperties.carbohydrates * grams) / 100;

      if (isProduct) {
        let portions = this.service.form.get('portions').value;
        energy /= portions;
        proteins /= portions;
        fats /= portions;
        carbohydrates /= portions;
      }

      return "Kcal: " + energy.toFixed(2) + "    B: " + proteins.toFixed(2) +
        "    T: " + fats.toFixed(2) + "    W: " + carbohydrates.toFixed(2);
    } else {
      return "";
    }
  }
}
