import { Component, OnInit } from '@angular/core';

import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FileService} from "../../service/file.service";

@Component({
  selector: 'app-generate-menu-panel',
  templateUrl: './generate-menu-panel.component.html',
  styleUrls: ['./generate-menu-panel.component.css']
})
export class GenerateMenuPanelComponent implements OnInit {

  constructor(
      private fileService: FileService,
      public dialogRef: MatDialogRef<GenerateMenuPanelComponent>
    ) { }

  fileServiceForm = this.fileService.form;

  public menuId: number;

  ngOnInit(): void {
    this.fileService.form.controls['menuId'].setValue(this.menuId);
  }

  onGeneratePDFButtonClick() {
    this.fileService.getPdfFile();
  }

  onClose() {
    this.dialogRef.close();
  }

}
