import {Component, OnInit, ViewChild} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialogRef} from "@angular/material/dialog";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {Product} from "../../model/product";

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

  categories: any = new Set();
  subcategories: any = new Set();

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

  getCategories() {
    let response = this.service.getCategories();
    response.subscribe(data => {
      for (const key of Object.values(data)) {
        this.categories.add(key.category);
        this.subcategories.add(key.subcategory);
      }
    });
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onSearchButtonClick() {
    this.service.getFilteredProducts(this.selectedCategory, this.selectedSubcategory)
      .subscribe(
        (data: Product[]) => {
          const productList = [ ...data];
          this.listData = new MatTableDataSource(productList);
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;
        });
  }

  onSelect(product) {
    this.dialogRef.close(product);
  }

  onClose() {
    this.dialogRef.close();
  }
}
