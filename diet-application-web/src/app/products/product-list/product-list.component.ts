import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../service/product.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ProductComponent} from "../product/product.component";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  constructor(
    private service: ProductService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['category', 'subcategory', 'name',
                                'energyValue', 'proteins', 'fats', 'carbohydrates',
                                'alergens', 'actions'];

  ngOnInit(): void {
    this.service.getProducts().subscribe(
      list => {
        let array = list.map(item => {
          return {
            id: item.id,
            header: item.header,
            primaryImageId: item.primaryImageId,
            type: item.type,
            name: item.name,
            category: item.category,
            subcategory: item.subcategory,
            energyValue: item.energyValue,
            proteins: item.proteins,
            fats: item.fats,
            saturatedFattyAcids: item.saturatedFattyAcids,
            monoUnsaturatedFattyAcids: item.monoUnsaturatedFattyAcids,
            polyUnsaturatedFattyAcids: item.polyUnsaturatedFattyAcids,
            cholesterol: item.cholesterol,
            carbohydrates: item.carbohydrates,
            sucrose: item.sucrose,
            dietaryFibres: item.dietaryFibres,
            sodium: item.sodium,
            potassium: item.potassium,
            calcium: item.calcium,
            phosphorus: item.phosphorus,
            magnesium: item.magnesium,
            iron: item.iron,
            selenium: item.selenium,
            betaCarotene: item.betaCarotene,
            vitaminD: item.vitaminD,
            vitaminC: item.vitaminC,
            lactose: item.lactose,
            starch: item.starch,
            gluten: item.gluten,
          };
        });
        this.listData = new MatTableDataSource(array);
      });
  }

  onEdit(product) {
    this.service.populateForm(product);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "90%";

    this.dialog.open(ProductComponent, dialogConfig);
  }

  onDelete(productId) {
    if (confirm("Are you sure to delete this product?")) {
      this.service.deleteProduct(productId);
      this.notificationService.warn(":: Deleted succesfully! ::");
    }
  }
}
