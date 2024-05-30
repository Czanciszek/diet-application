import { Injectable } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { RestapiService } from "./restapi.service";
import { CalendarDateValidator } from "../utils/calendarDateValidator"

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(
    private restApiService: RestapiService
  ) { }

  patientList: any;

  form: UntypedFormGroup = new UntypedFormGroup({
    id: new UntypedFormControl(null),
    name: new UntypedFormControl('', [Validators.required, Validators.minLength(2)]),
    surname: new UntypedFormControl('', [Validators.required, Validators.minLength(2)]),
    birthDate: new UntypedFormControl(null, [Validators.required, CalendarDateValidator]),
    numberPhone: new UntypedFormControl(''),
    email: new UntypedFormControl('', [Validators.email]),
    sex: new UntypedFormControl(null),
    bodyHeight: new UntypedFormControl(null, [Validators.required]),
    currentLifestyleNote: new UntypedFormControl(''),
    changedLifestyleNote: new UntypedFormControl(''),
    dietaryPurpose: new UntypedFormControl(''),
    allergens: new UntypedFormControl(null),
    unlikelyCategories: new UntypedFormControl(null),
    measurements: new UntypedFormControl(null)
  });

  initializeFormGroup() {
    this.form.setValue({
      id: null,
      name: '',
      surname: '',
      birthDate: null,
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
