import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {GlobalVariable} from "../global";
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

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
    header: new FormControl(null),
    primaryImageId: new FormControl(null),
    type: new FormControl(''),
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
      header: null,
      primaryImageId: null,
      type: '',
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
    this.patientList = this.http.get("http://localhost:8080/api/v1/patients", this.httpOptions);
    return this.patientList;
  }

  getPatientById(patientId) {
    this.http.get("http://localhost:8080/api/v1/patients/" + patientId, this.httpOptions)
      .toPromise().then(
      result => {
        this.populateForm(result);
      }
    );
  }

  insertPatient(patient) {
    return this.http.post("http://localhost:8080/api/v1/patients", patient, this.httpOptions);
  }

  updatePatient(patient) {
    return this.http.put("http://localhost:8080/api/v1/patients/" + patient.id, patient, this.httpOptions);
  }

  deletePatient(id: string) {
    return this.http.delete("http://localhost:8080/api/v1/patients/" + id, this.httpOptions).subscribe();
  }

  populateForm(patient) {
    this.form.setValue(patient);
  }
}
