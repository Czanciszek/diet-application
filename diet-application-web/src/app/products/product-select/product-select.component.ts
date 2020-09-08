import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialogRef} from "@angular/material/dialog";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-product-select',
  templateUrl: './product-select.component.html',
  styleUrls: ['./product-select.component.css']
})
export class ProductSelectComponent implements OnInit {

  constructor(
    private service: ProductService,
    public dialogRef: MatDialogRef<ProductSelectComponent>,
  ) { }

  categories: any;
  subcategories: any = [];

  selectedCategory: "";
  selectedSubcategory: "";

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['category', 'subcategory', 'name',
    'energyValue', 'proteins', 'fats', 'carbohydrates',
    'alergens', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.getCategories();
  }

  getProducts() {
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
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      })
  }

  getCategories() {
    let response = this.service.getCategories();
    response.subscribe(data => {
      this.categories = data;
      for (const key of Object.values(data))
        this.subcategories = this.subcategories.concat(key.subcategories);
    });
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onSearchButtonClick() {
    this.service.getFilteredProducts(this.selectedCategory, this.selectedSubcategory)
      .subscribe(
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
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;
        })
  }

  onSelect(product) {
    this.dialogRef.close(product);
  }

  onClose() {
    this.dialogRef.close();
  }
}
