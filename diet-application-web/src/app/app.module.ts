import { BrowserModule } from '@angular/platform-browser';
import {forwardRef, NgModule} from '@angular/core';
import {MaterialModule} from "./material/material.module";

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import {RestapiService} from "./service/restapi.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule} from "@angular/forms";
import { AppRoutingModule } from './app-routing.module';
import {RouterModule, Routes} from "@angular/router";
import { ProductsComponent } from './products/products.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductComponent } from './products/product/product.component';
import {ProductService} from "./service/product.service";
import { DishesComponent } from './dishes/dishes.component';
import { ProductListComponent } from './products/product-list/product-list.component';
import {NotificationService} from "./service/notification.service";
import { DishListComponent } from './dishes/dish-list/dish-list.component';
import { DishComponent } from './dishes/dish/dish.component';
import {DishService} from "./service/dish.service";
import { ProductSelectComponent } from './products/product-select/product-select.component';

const routes: Routes = [
  {path: "", redirectTo:"login", pathMatch:"full"},
  {path: "login", component:LoginComponent},
  {path: "home", component:HomeComponent},
  {path: "products", component:ProductsComponent},
  {path: "dishes", component:DishesComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ProductsComponent,
    ProductComponent,
    ProductListComponent,
    ProductSelectComponent,
    DishesComponent,
    DishListComponent,
    DishComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MaterialModule,
  ],
  providers: [
    RestapiService,
    ProductService,
    DishService,
    NotificationService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    ProductsComponent,
    DishesComponent,
    ProductSelectComponent,
  ],
})
export class AppModule { }
