import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RestapiService} from "./restapi.service";

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(
    private restApiService: RestapiService
  ) { }

  patientList: any;

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    name: new FormControl('', [Validators.required, Validators.minLength(2)]),
    surname: new FormControl('', [Validators.required, Validators.minLength(2)]),
    birthDate: new FormControl('', [Validators.required]),
    numberPhone: new FormControl(''),
    email: new FormControl('', [Validators.email]),
    sex: new FormControl(null),
    bodyHeight: new FormControl(null, [Validators.required]),
    currentLifestyleNote: new FormControl(''),
    changedLifestyleNote: new FormControl(''),
    dietaryPurpose: new FormControl(''),
    allergens: new FormControl(null),
    unlikelyCategories: new FormControl(null),
    measurements: new FormControl(null)
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      name: '',
      surname: '',
      birthDate: '',
      numberPhone: '',
      email: '',
      sex: null,
      bodyHeight: null,
      currentLifestyleNote: '',
      changedLifestyleNote: '',
      dietaryPurpose: '',
      allergens: null,
      unlikelyCategories: null,
      measurements: null
    })
  }

  populateForm(patient) {
    this.form.setValue(patient);
  }

  getPatients() {
    return this.restApiService.get("patients", "v2");
  }

  getPatientById(patientId) {
    return this.restApiService.get("patients/" + patientId, "v2");
  }

  insertPatient(patient) {
    return this.restApiService.post(patient, "patients", "v2");
  }

  insertMeasurement(measurement) {
    return this.restApiService.post(measurement, "patients/measurements", "v2");
  }

  updatePatient(patient) {
    return this.restApiService.put(patient, "patients", "v2");
  }

  deletePatient(id: string) {
    return this.restApiService.delete("patients/" + id, "v2");
  }
}
