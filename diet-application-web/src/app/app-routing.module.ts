import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";

  const routes: Routes = [
    {path: "", redirectTo:"login", pathMatch:"full"},
    {path: "login", component:LoginComponent},
    {path: "home", component:HomeComponent}
  ];

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class AppRoutingModule { }
