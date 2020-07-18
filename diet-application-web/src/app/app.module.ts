import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import {RestapiService} from "./service/restapi.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { AppRoutingModule } from './app-routing.module';
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {path: "", redirectTo:"login", pathMatch:"full"},
  {path: "login", component:LoginComponent},
  {path: "home", component:HomeComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    RouterModule.forRoot(routes)
  ],
  providers: [RestapiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
