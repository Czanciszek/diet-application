import {Component, OnInit, ViewChild, EventEmitter, Input, Output} from '@angular/core';
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

  productList: MatTableDataSource<Product> = new MatTableDataSource();
  displayedColumns: string[] = ['category', 'subcategory', 'name',
    'energyValue', 'proteins', 'fats', 'carbohydrates',
    'alergens', 'actions'];

  private paginator: MatPaginator;
  private sort: MatSort;

  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.setDataSourceAttributes();
  }

  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    this.setDataSourceAttributes();
  }

  setDataSourceAttributes() {
    this.productList.paginator = this.paginator;
    this.productList.sort = this.sort;

    this.productList.filterPredicate = (product: Product, filter: string) => {
    return product.name == null ||
      product.name.toLowerCase().includes(filter) ||
      product.category.toLowerCase().includes(filter) ||
      product.subcategory.toLowerCase().includes(filter);
    };

    if (this.paginator && this.sort) {
      this.applyFilter();
    }
  }

  searchKey: string = "";

  @Input()
  isReplaceAction: boolean = false;

  @Output()
  productSelected = new EventEmitter<Product>();

  ngOnInit() { }

  ngAfterViewInit() {
    if (this.productService.productList == null || this.productService.productList.length == 0) {
      this.fetchProducts();
    } else {
      this.getProducts();
    }
  }

  applyFilter() {
    this.productList.filter = this.searchKey.trim().toLowerCase();
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  onRefreshButtonClick() {
    this.fetchProducts();
  }

  fetchProducts() {
    this.productService.getProducts()
      .subscribe( (data: Product[]) => {
        this.productService.productList = [ ...data];
        this.getProducts();
        this.applyFilter();
    });
  }

  getProducts() {
    this.productList.data = [ ...this.productService.productList];
  }

  onSelect(product) {
    if (this.isReplaceAction) {
      // TODO: Ask for confirmation Old Product -> New Product
      this.productSelected.emit(product);
    } else {
      this.dialogRef.close(product);
    }
  }

  onClose() {
    this.dialogRef.close();
  }
}
