import { Component, OnInit } from '@angular/core';

import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FileService} from "../../service/file.service";
import {MenuService} from "../../service/menu.service";

@Component({
  selector: 'app-generate-menu-panel',
  templateUrl: './generate-menu-panel.component.html',
  styleUrls: ['./generate-menu-panel.component.css']
})
export class GenerateMenuPanelComponent implements OnInit {

  constructor(
      private fileService: FileService,
      private menuService: MenuService,
      public dialogRef: MatDialogRef<GenerateMenuPanelComponent>
    ) { }

  fileServiceForm = this.fileService.form;

  public menuId: number;
  public recommendations: string;

  ngOnInit(): void {
    this.fileService.form.controls['menuId'].setValue(this.menuId);
    this.fileService.form.controls['recommendations'].setValue(this.recommendations);
  }

  onGeneratePDFButtonClick() {
    this.fileService
      .getPdfFile()
      .subscribe(
        (response) => {
        this.fileService.downloadFile(response, "application/pdf");
        this.dialogRef.close();
      });
  }

  onClose() {
    this.dialogRef.close();
  }

}
