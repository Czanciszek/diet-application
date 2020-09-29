import { Component, OnInit } from '@angular/core';
import {PatientService} from "../../service/patient.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private service: PatientService,
  ) { }

  ngOnInit(): void {
    let patientId = this.route.snapshot.paramMap.get("patient_id");
    this.service.getPatientById(patientId);
  }

}
