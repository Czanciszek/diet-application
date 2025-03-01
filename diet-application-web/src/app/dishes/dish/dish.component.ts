import { Component, OnInit } from '@angular/core';
import { NotificationService } from "../../service/notification.service";
import { MatDialog, MatDialogRef } from "@angular/material/dialog";
import { DishService } from "../../service/dish.service";
import { FormArray } from "@angular/forms";
import { ProductSelectComponent } from "../../products/product-select/product-select.component";
import { ProductService } from "../../service/product.service";
import { FOOD_TYPES } from "../../model/helpers/foodTypes";
import { AMOUNT_TYPES } from "../../model/helpers/amountTypes";

@Component({
    selector: 'app-dish',
    templateUrl: './dish.component.html',
    styleUrls: ['./dish.component.css'],
    standalone: false
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
    let formProducts = <FormArray>this.dishForm.get('products');
    this.selectProduct(formProducts.length);
    formProducts.push(this.dishService.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.dishForm.get('products')).removeAt(productIndex);
  }

  selectProduct(productIndex) {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(selectedProduct => {
      if (selectedProduct == null) { return; }

      // Update value in Form Group
      this.dishService.setupProductAmountTypes(selectedProduct.amountTypes, productIndex);

      let products = (<FormArray>this.dishService.form.get('products'));
      products.at(productIndex).get('productId').patchValue(selectedProduct.id);
      products.at(productIndex).get('productName').patchValue(selectedProduct.name);
      products.at(productIndex).get('amountTypes').patchValue(selectedProduct.amountTypes);

      let initialAmountType = (selectedProduct.amountTypes?.length > 0) ? selectedProduct.amountTypes[0].amountType : null;
      products.at(productIndex).get('amountType').patchValue(initialAmountType);

      this.dishService.form.patchValue({
        products: [products]
      });
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
      if (product == null || this.productService.productList == null || product.grams == ".") {
        continue;
      }

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

  displayAmountType(amountType: string) {
    if (amountType == null) return;
    let selectedAmountType = this.amountTypes.find(type => type.id == amountType);
    return selectedAmountType.value;
  }

  validateProperty(value, productIndex: number, property: string) {
    let number = this.validateNumber(value);

    let product = this.dishService.form.get('products').get([productIndex]);
    product.get(property).patchValue(number);

    this.productPropertiesChanged(productIndex, property);
  }

  productPropertiesChanged(productIndex: number, property: string) {
    if (productIndex == null) { return; }

    let product = this.dishService.form.get('products').get([productIndex]);
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
    let value = (entry).replace(/,/g, '.').split("").reverse().join("").replace(/(\.)(?=.*\1)/g, '').split("").reverse().join("").replace(/[^0-9\.]+/g, '');
    if (value != "" && value != "." && value.includes(".") && value.substring(value.indexOf('.') + 1).length > 2) {
      value = Number(value).toFixed(2);
    }

    return value;
  }

  amountTypeChanged(productIndex) {
    let property = 'grams';
    let grams = this.dishService.form.get('products').get([productIndex]).get(property).value;
    if (grams == null || grams == "") return;
    this.validateProperty(grams + '', productIndex, property);
  }
}
