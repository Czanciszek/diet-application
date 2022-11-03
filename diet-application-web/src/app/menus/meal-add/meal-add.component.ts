import { Component, OnInit } from '@angular/core';
import { MealService } from "../../service/meal.service";
import { NotificationService } from "../../service/notification.service";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { ProductSelectComponent } from "../../products/product-select/product-select.component";
import { FormArray } from "@angular/forms";
import { DishSelectComponent } from "../../dishes/dish-select/dish-select.component";
import { ProductService } from "../../service/product.service";
import { FOOD_TYPES } from "../../model/helpers/foodTypes";
import { AMOUNT_TYPES } from "../../model/helpers/amountTypes";

@Component({
  selector: 'app-meal-add',
  templateUrl: './meal-add.component.html',
  styleUrls: ['./meal-add.component.css']
})
export class MealAddComponent implements OnInit {

  constructor(
    private mealService: MealService,
    private productService: ProductService,
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

  dishForm: any;
  singleProductForm: any;

  ngOnInit(): void {
    if (this.mealServiceForm.get('id').value == null) {
      let formProducts = <FormArray>this.mealServiceForm.get('productList');
      let productForm = this.mealService.addProductFormGroup();
      formProducts.push(productForm);
      return;
    }

    this.checkPortionOption();
    this.tabIndex = this.mealService.form.get('isProduct').value;
    this.blockDish = true;
    if (this.tabIndex == 0) {
      this.portionCurrentValue = this.mealService.form.get("portions").value;
    }
  }

  onClose() {
    this.mealService.clearForm();
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.mealServiceForm.invalid) {
      let invalidName = this.mealServiceForm.get('name').invalid;
      let dishTab = this.tabIndex == 0;
      let invalidProducts = this.mealServiceForm.value.productList.length == 1 || this.mealServiceForm.get('productList').get('0').invalid;

      if (dishTab || invalidName || invalidProducts) {
        this.notificationService.warn(":: Uzupełnij wymagane pola! ::");
        return;
      }
    }

    if (!this.mealService.form.get('id').value) {
      this.mealService.insertMeal(this.mealService.form.value).subscribe(result => {
        this.notificationService.success("::Dodano posiłek pomyślnie! ::");
        this.onClose();
      });
    } else {
      this.mealService.updateMeal(this.mealService.form.value).subscribe(result => {
        this.notificationService.success("::Posiłek zaktualizowany pomyślnie! ::");
        this.onClose();
      });
    }
  }

  checkPortionOption() {
    this.withPortions = this.mealService.form.get('grams').value == 0;
  }

  addProductButtonClick() {
    let formProducts = <FormArray>this.mealServiceForm.get('productList');
    this.selectProductForDish(formProducts.length);
    formProducts.push(this.mealService.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.mealService.form.get('productList')).removeAt(productIndex);
  }

  selectProductForDish(productIndex) {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(selectedProduct => {
      if (selectedProduct == null) { return; }

      // Update value in Form Group
      this.mealService.setupProductAmountTypes(selectedProduct.amountTypes, productIndex);

      // Update value in Form Group
      let products = (<FormArray>this.mealServiceForm.get('productList'));
      products.at(productIndex).get('productId').patchValue(selectedProduct.id);
      products.at(productIndex).get('productName').patchValue(selectedProduct.name);
      products.at(productIndex).get('amountTypes').patchValue(selectedProduct.amountTypes);

      let initialAmountType = (selectedProduct.amountTypes?.length > 0) ? selectedProduct.amountTypes[0].amountType : null;
      products.at(productIndex).get('amountType').patchValue(initialAmountType);

    });
  }

  selectProduct() {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(selectedProduct => {
      if (selectedProduct == null) { return; }

      // Update value in Form Group
      this.mealService.form.get('name').patchValue(selectedProduct.name);
      this.mealService.form.get('isProduct').patchValue(1);

      let productForm = this.mealService.form.get('productList').get('0');
      productForm.get('productId').patchValue(selectedProduct.id);
      productForm.get('productName').patchValue(selectedProduct.name);

      for (let amountType of selectedProduct.amountTypes) {
        let amountTypeForm = this.mealService.addAmountTypeFormGroup();
        amountTypeForm.get('grams').patchValue(amountType.grams);
        amountTypeForm.get('amountType').patchValue(amountType.amountType);
        (<FormArray>productForm.get('amountTypes')).push(amountTypeForm);
      }

      let initialAmountType = (selectedProduct.amountTypes?.length > 0) ? selectedProduct.amountTypes[0].amountType : null;
      productForm.get('amountType').patchValue(initialAmountType);

      let grams = productForm.get('grams').value;
      if (grams == null || grams == "") {
        productForm.get('grams').patchValue("100");
      }

      let amount = productForm.get('amount').value;
      if (amount == null || amount == "") {
        productForm.get('amount').patchValue("1");
      }

      this.validateProperty(productForm.get('grams').value, 0, 'grams');
    });
  }

  selectDish() {

    let dialogRef = this.dialog.open(DishSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(selectedDish => {
      if (selectedDish == null) { return; }

      (<FormArray>this.mealService.form.get('productList')).clear();

      // Update value in Form Group
      this.mealService.form.get('name').patchValue(selectedDish.name);
      this.mealService.form.get('recipe').patchValue(selectedDish.recipe);
      this.mealService.form.get('dishPortions').patchValue(selectedDish.portions);
      this.mealService.form.get('isProduct').patchValue(0);

      for (let product of selectedDish.products) {
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

        for (let amountType of product.amountTypes) {
          let amountTypeForm = this.mealService.addAmountTypeFormGroup();
          amountTypeForm.get('grams').patchValue(amountType.grams);
          amountTypeForm.get('amountType').patchValue(amountType.amountType);
          (<FormArray>productForm.get('amountTypes')).push(amountTypeForm);
        }

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
      if (product == null || this.productService.productList == null || product.grams == ".") {
        continue;
      }

      let isProduct = (this.mealService.form.get('isProduct').value == 1);

      let originProduct = this.productService.productList.find(p => {
        return p.id == product.productId;
      });

      if (originProduct == null) continue;
      let foodProperties = originProduct.foodProperties;
      let grams = product.grams;

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

  displayAmountType(amountType: string) {
    if (amountType == null) return;
    let selectedAmountType = this.amountTypes.find(type => type.id == amountType);
    return selectedAmountType.value;
  }

  validateProperty(newValue: string, productIndex: number, property: string) {
    console.log("VALIDATING:", newValue);
    let number = this.validateNumber(newValue);

    let product = this.mealServiceForm.get('productList').get([productIndex]);
    product.get(property).patchValue(number);

    this.productPropertiesChanged(productIndex, property);
  }

  productPropertiesChanged(productIndex: number, property: string) {
    if (productIndex == null) { return; }

    let product = this.mealServiceForm.get('productList').get([productIndex]);
    let selectedAmountType = product.value.amountTypes?.find(type => type.amountType == product.value.amountType);

    if (product.value.grams == null
      || product.value.amount == null
      || product.value.amountType == null
      || selectedAmountType == null) { return; }

    let productGrams = product.value.grams;
    let productAmount = product.value.amount;
    if (property == 'grams') {
      if (productGrams == ".") {
        productAmount = 0;
      } else {
        productAmount = ((productGrams * 1.0) / selectedAmountType.grams).toFixed(2);
      }

    } else if (property == 'amount') {
      if (productAmount == ".") {
        productGrams = 0;
      } else {
        productGrams = ((productAmount * 1.0) * selectedAmountType.grams).toFixed(2);
      }
    }

    product.get('grams').patchValue(productGrams);
    product.get('amount').patchValue(productAmount);
  }

  validateNumber(entry: string) {
    let value = entry.replace(/,/g, '.').split("").reverse().join("").replace(/(\.)(?=.*\1)/g, '').split("").reverse().join("").replace(/[^0-9\.]+/g, '');

    if (value != "" && value != "." && value.includes(".") && value.substring(value.indexOf('.') + 1).length > 2) {
      value = Number(value).toFixed(2);
    }

    return value;
  }

  amountTypeChanged(productIndex) {
    let property = 'grams';
    let grams = this.mealService.form.get('productList').get([productIndex]).get(property).value;
    if (grams == null || grams == "") return;
    this.validateProperty(grams + '', productIndex, property);
  }

}
