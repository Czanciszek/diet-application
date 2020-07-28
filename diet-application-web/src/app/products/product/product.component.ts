import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../service/product.service";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  constructor(
    private service: ProductService,
  ) { }

  categories = [
    { id: 1, value: "Cat1"},
    { id: 2, value: "Cat2"},
    { id: 3, value: "Cat3"},
  ];

  ngOnInit(): void {
  }

  onClear() {
    this.service.form.reset();
    this.service.initializeFormGroup();
  }
}
