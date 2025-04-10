import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MaterialModule } from "./material/material.module";
import { FlexLayoutModule } from '@angular/flex-layout';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { RestapiService } from "./service/restapi.service";
import { provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AppRoutingModule } from './app-routing.module';
import { RouterModule, Routes } from "@angular/router";
import { ProductsComponent } from './products/products.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductComponent } from './products/product/product.component';
import { ProductService } from "./service/product.service";
import { DishesComponent } from './dishes/dishes.component';
import { ProductListComponent } from './products/product-list/product-list.component';
import { NotificationService } from "./service/notification.service";
import { DishListComponent } from './dishes/dish-list/dish-list.component';
import { DishComponent } from './dishes/dish/dish.component';
import { DishSelectorService } from './service/dish-selector.service';
import { DishService } from "./service/dish.service";
import { ProductSelectComponent } from './products/product-select/product-select.component';
import { PatientsComponent } from './patients/patients.component';
import { PatientListComponent } from './patients/patient-list/patient-list.component';
import { PatientService } from "./service/patient.service";
import { PatientComponent } from './patients/patient/patient.component';
import { PatientEditComponent } from './patients/patient-edit/patient-edit.component';
import { MeasurementListComponent } from './measurements/measurement-list/measurement-list.component';
import { MenuListComponent } from './menus/menu-list/menu-list.component';
import { WeekViewComponent } from './menus/week-view/week-view.component';
import { DishTypeComponent } from './menus/dish-type/dish-type.component';
import { DishSummaryComponent } from './menus/dish-summary/dish-summary.component';
import { DishAddComponent } from './menus/dish-add/dish-add.component';
import { MenusComponent } from './menus/menus.component';
import { MenuAddComponent } from './menus/menu-add/menu-add.component';
import { MealAddComponent } from './menus/meal-add/meal-add.component';
import { DishSelectComponent } from './dishes/dish-select/dish-select.component';
import { WeekHeaderComponent } from './menus/week-header/week-header.component';
import { GenerateMenuPanelComponent } from './menus/generate-menu-panel/generate-menu-panel.component';
import { CopyMenuPanelComponent } from './menus/copy-menu-panel/copy-menu-panel.component';
import { RegisterComponent } from './register/register.component';
import { ReplaceProductPanelComponent } from './menus/replace-product-panel/replace-product-panel.component';
import { MenuProductsComponent } from './menus/menu-products/menu-products.component';

const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  { path: "home", component: HomeComponent },
  { path: "products", component: ProductsComponent },
  { path: "dishes", component: DishesComponent },
  { path: "patients", component: PatientsComponent },
  { path: "menu/:menu_id", component: WeekViewComponent },
  { path: "patients/:patient_id", component: PatientComponent },
  { path: "patients/:patient_id/menus", component: MenusComponent },
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
    PatientsComponent,
    PatientListComponent,
    PatientComponent,
    PatientEditComponent,
    MeasurementListComponent,
    MenuListComponent,
    WeekViewComponent,
    DishTypeComponent,
    DishSummaryComponent,
    DishAddComponent,
    MenusComponent,
    MenuAddComponent,
    MealAddComponent,
    DishSelectComponent,
    WeekHeaderComponent,
    GenerateMenuPanelComponent,
    CopyMenuPanelComponent,
    RegisterComponent,
    ReplaceProductPanelComponent,
    MenuProductsComponent,
  ],
  bootstrap: [AppComponent],
  imports: [BrowserModule,
    FormsModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule],
  providers: [
    RestapiService,
    ProductService,
    DishSelectorService,
    DishService,
    PatientService,
    NotificationService,
    provideHttpClient(withInterceptorsFromDi()),
  ]
})
export class AppModule { }
