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

  categoryList: any[] = [];
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
    this.onSearchButtonClick();
  }

  getCategories() {
    let response = this.service.getCategories();
    response.subscribe(data => {
      this.categoryList = Object.values(data);
      for (const key of this.categoryList) {
        this.categories.add(key.category);
        this.subcategories.add(key.subcategory);
      }
    });
  }

  fillSubcategories(categories) {
    for (const key of categories) {
      this.subcategories.add(key.subcategory);
    }
  }

  onCategoryChange(category) {
    if (category == null) return;
    this.subcategories.clear();
    const filteredCategories = this.categoryList.filter( x => !!x.category && x.category.includes(category));
    this.fillSubcategories(filteredCategories);
    this.selectedSubcategory = null;
  }

  onSubcategoryChange(subcategory) {
    if (subcategory == null) return;
    const filteredCategory = this.categoryList.find( x => !!x.subcategory && x.subcategory.includes(subcategory));

    this.onCategoryChange(filteredCategory.category);
    this.selectedCategory = filteredCategory.category;
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  onSearchButtonClick() {
    this.service.getFilteredProducts(this.selectedCategory, this.selectedSubcategory)
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
