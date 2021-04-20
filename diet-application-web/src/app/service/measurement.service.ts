import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Measurement} from "../model/measurement";

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
  measurementObject: any;
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
    patientId: new FormControl(null),
    measurementDate: new FormControl('', [Validators.required]),
    bodyWeight: new FormControl(null),
    breast: new FormControl(null),
    underBreast: new FormControl(null),
    waist: new FormControl(null),
    abdominal: new FormControl(null),
    hipBones: new FormControl(null),
    hips: new FormControl(null),
    thighWidest: new FormControl(null),
    thighNarrowest: new FormControl(null),
    calf: new FormControl(null),
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
      patientId: null,
      measurementDate: null,
      bodyWeight: null,
      breast: null,
      underBreast: null,
      waist: null,
      abdominal: null,
      hipBones: null,
      hips: null,
      thighWidest: null,
      thighNarrowest: null,
      calf: null,
      chest: null,
      arm: null,
    })
  }

  getMeasurementsById(measurementId) {
    return this.http.get<Measurement>("http://localhost:8080/api/v1/measurements/" + measurementId, this.httpOptions);
  }

  getMeasurementsByPatientId(patientId) {
    this.measurementList = this.http.get("http://localhost:8080/api/v1/measurements/patient/" + patientId, this.httpOptions).pipe();
    return this.measurementList;
  }

  populateForm(measurement) {
    this.form.setValue(measurement);
  }

  insertMeasurement(measurement, patientId) {
    return this.http.post("http://localhost:8080/api/v1/measurements/" + patientId, measurement, this.httpOptions);
  }
}
