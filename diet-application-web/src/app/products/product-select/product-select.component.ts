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
    private productService: ProductService,
    public dialogRef: MatDialogRef<ProductSelectComponent>,
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['category', 'subcategory', 'name',
    'energyValue', 'proteins', 'fats', 'carbohydrates',
    'alergens', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.getProducts();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  getProducts() {
    this.productService.getProducts()
      .subscribe(
        (data: Product[]) => {
          const productList = [ ...data];
          this.listData = new MatTableDataSource(productList);
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;

          this.listData.filterPredicate = (data: Product, filter: string) => {
            return data.name == null ||
              data.name.toLowerCase().includes(filter) ||
              data.category.toLowerCase().includes(filter) ||
              data.subcategory.toLowerCase().includes(filter);
          };
        });
  }

  onSelect(product) {
    this.dialogRef.close(product);
  }

  onClose() {
    this.dialogRef.close();
  }
}
