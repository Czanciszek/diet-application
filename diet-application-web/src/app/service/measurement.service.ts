import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  patientId: '';
  measurementList: any;

  measurementKeywords = new Map([ ['measurementDate', 'Data pomiaru'], ['bodyWeight', 'Masa ciała'], ['waist', 'Talia'],
    ['abdominal', 'Pas'], ['hips', 'Biodra'], ['thighWidest', 'Udo najszersze'],
    ['calf', 'Łydka'], ['breast', 'Biust'], ['underBreast', 'Pod biustem'],
    ['hipBones', 'Kości biodrowe'], ['thighNarrowest', 'Udo najwęższe'], ['chest', 'Klatka piersiowa'], ['arm', 'Ramię'] ]);


  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    header: new FormControl(null),
    primaryImageId: new FormControl(null),
    type: new FormControl(''),
    name: new FormControl(''),
    patientDocRef: new FormControl(null),
    measurementDate: new FormControl('', [Validators.required]),
    // Measurements only for all
    bodyWeight: new FormControl(null),
    waist: new FormControl(null),
    abdominal: new FormControl(null),
    hips: new FormControl(null),
    thighWidest: new FormControl(null),
    calf: new FormControl(null),
    // Measurements only for women
    breast: new FormControl(null),
    underBreast: new FormControl(null),
    hipBones: new FormControl(null),
    thighNarrowest: new FormControl(null),
    // Measurements only for men
    chest: new FormControl(null),
    arm: new FormControl(null),
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      header: null,
      primaryImageId: null,
      type: '',
      name: '',
      patientDocRef: null,
      measurementDate: null,
      // Measurements only for all
      bodyWeight: null,
      waist: null,
      abdominal: null,
      hips: null,
      thighWidest: null,
      calf: null,
      // Measurements only for women
      breast: null,
      underBreast: null,
      hipBones: null,
      thighNarrowest: null,
      // Measurements only for men
      chest: null,
      arm: null,
    })
  }

  getMeasurementsByPatientId(patientId) {
    this.measurementList = this.http.get("http://localhost:8080/api/v1/measurements/patient/" + patientId, this.httpOptions);
    return this.measurementList;
  }

  populateForm(measurement) {
    this.form.setValue(measurement);
  }

  insertMeasurement(measurement, patientId) {
    return this.http.post("http://localhost:8080/api/v1/measurements/" + patientId, measurement, this.httpOptions);
  }
}
