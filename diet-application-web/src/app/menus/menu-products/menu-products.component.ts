import {Component, OnInit, ViewChild, Output, EventEmitter} from '@angular/core';
import {MenuService} from "../../service/menu.service";
import {ShoppingProduct} from "../../model/shopping-product";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-menu-products',
  templateUrl: './menu-products.component.html',
  styleUrls: ['./menu-products.component.css']
})
export class MenuProductsComponent implements OnInit {

  constructor(
    private menuService: MenuService
  ) { }

  @Output()
  productSelected = new EventEmitter<ShoppingProduct>();

  menuProductList: MatTableDataSource<ShoppingProduct> = new MatTableDataSource();
  displayedColumns: string[] = ['categoryName', 'productName', 'grams', 'actions'];

  private sort: MatSort;

  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    this.menuProductList.sort = this.sort;
  }

  searchKey: string = "";

  applyFilter() {
    this.menuProductList.filter = this.searchKey.trim().toLowerCase();
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  ngOnInit(): void {
    let menuId = this.menuService.form.get("id").value;
    this.getMenuProducts(menuId);
  }

  getMenuProducts(menuId) {
    this.menuService.getMenuProducts(menuId)
      .subscribe(
        (data: ShoppingProduct[]) => {
          this.menuProductList.data = [ ...data];
      });
  }

  onProductSelect(product) {
    this.productSelected.emit(product);
  }
}
