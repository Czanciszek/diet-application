import { Component } from '@angular/core';
import { ProductReplacement } from "../../model/product-replacement";
import { MatDialogRef } from "@angular/material/dialog";

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
  oldProductId: string

  chooseReplacement(productId) {
    this.oldProductId = productId;
    this.blockReplace = false;
    this.tabIndex = 1;
  }

  replacementSelected(newProduct) {
    this.dialogRef.close(new ProductReplacement(this.oldProductId, newProduct.id));
  }

  onClose() {
    this.dialogRef.close();
  }

}
