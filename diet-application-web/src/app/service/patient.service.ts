import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  serverAddress = "http://localhost:8080/";
  dbService = "api/psql/";

  httpOptions = {
    headers: new HttpHeaders({
      Authorization: 'Basic '+
        btoa(GlobalVariable.CURRENT_USER_LOGIN + ":" + GlobalVariable.CURRENT_USER_PASSWORD)})
  };

  constructor(
    private http: HttpClient
  ) { }

  patientList: any;

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    birthDate: new FormControl('', [Validators.required]),
    numberPhone: new FormControl(''),
    email: new FormControl('', [Validators.email]),
    sex: new FormControl(false),
    bodyWeight: new FormControl(null),
    bodyHeight: new FormControl(null),
    currentLifestyleNote: new FormControl(''),
    changedLifestyleNote: new FormControl(''),
    dietaryPurpose: new FormControl(''),
    measurements: new FormControl(null),
    //interviewanswers
    //diets
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      name: '',
      birthDate: '',
      numberPhone: '',
      email: '',
      sex: false,
      bodyHeight: null,
      bodyWeight: null,
      currentLifestyleNote: '',
      changedLifestyleNote: '',
      dietaryPurpose: '',
      measurements: null,
    })
  }

  getPatients() {
    this.patientList = this.http.get(this.serverAddress + this.dbService + "patients", this.httpOptions);
    return this.patientList;
  }

  getPatientById(patientId) {
    this.http.get(this.serverAddress + this.dbService + "patients/" + patientId, this.httpOptions)
      .toPromise().then(
      result => {
        this.populateForm(result);
      }
    );
  }

  insertPatient(patient) {
    return this.http.post(this.serverAddress + this.dbService + "patients", patient, this.httpOptions);
  }

  updatePatient(patient) {
    return this.http.put(this.serverAddress + this.dbService + "patients/" + patient.id, patient, this.httpOptions);
  }

  deletePatient(id: string) {
    return this.http.delete(this.serverAddress + this.dbService + "patients/" + id, this.httpOptions).subscribe();
  }

  populateForm(patient) {
    this.form.setValue(patient);
  }
}
