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

const routes: Routes = [
  {path: "", redirectTo:"login", pathMatch:"full"},
  {path: "login", component:LoginComponent},
  {path: "home", component:HomeComponent},
  {path: "products", component:ProductsComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ProductsComponent,
    ProductComponent,
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
  ],
  bootstrap: [AppComponent],
  entryComponents: [ProductsComponent],
})
export class AppModule { }
