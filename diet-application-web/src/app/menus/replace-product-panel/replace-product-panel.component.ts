import { Component } from '@angular/core';
import {ShoppingProduct} from "../../model/shopping-product";
import {ProductReplacement} from "../../model/product-replacement";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-replace-product-panel',
  templateUrl: './replace-product-panel.component.html',
  styleUrls: ['./replace-product-panel.component.css']
})
export class ReplaceProductPanelComponent {

  constructor(
    public dialogRef: MatDialogRef<ReplaceProductPanelComponent>
  ) { }

  tabIndex = 0;
  blockReplace: boolean = true
  oldProduct: ShoppingProduct

  chooseReplacement(product) {
    this.oldProduct = product;
    this.blockReplace = false;
    this.tabIndex = 1;
  }

  replacementSelected(newProduct) {
    this.dialogRef.close(new ProductReplacement(this.oldProduct, newProduct));
  }

  onClose() {
    this.dialogRef.close();
  }

}
