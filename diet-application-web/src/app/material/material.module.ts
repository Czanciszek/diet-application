import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatToolbarModule}  from "@angular/material/toolbar";
import {MatDialogModule} from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from "@angular/material/button";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatRadioModule} from "@angular/material/radio";
import {MatSelectModule} from "@angular/material/select";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatPaginatorIntl, MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {getPolishPaginatorIntl} from "./helper/polish-paginator-ingtl";
import {MatSliderModule} from "@angular/material/slider";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule,
    MatFormFieldModule,
    MatRadioModule,
    MatSelectModule,
    MatCheckboxModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSliderModule,
  ],
  exports: [
    MatToolbarModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule,
    MatFormFieldModule,
    MatRadioModule,
    MatSelectModule,
    MatCheckboxModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatSliderModule,
  ],
  providers: [
    MatSnackBar,
    { provide: MatPaginatorIntl, useValue: getPolishPaginatorIntl() }
  ]
})
export class MaterialModule { }
