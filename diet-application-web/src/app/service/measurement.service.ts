import { Injectable } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { CalendarDateValidator } from "../utils/calendarDateValidator"

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {

  measurementKeywords = new Map([
    ['measurementDate', 'Data pomiaru'],
    ['bodyWeight', 'Masa ciała'],
    ['waist', 'Talia'],
    ['abdominal', 'Pas'],
    ['hips', 'Biodra'],
    ['thighWidest', 'Udo najszersze'],
    ['calf', 'Łydka'],
    ['breast', 'Biust'],
    ['underBreast', 'Pod biustem'],
    ['hipBones', 'Kości biodrowe'],
    ['thighNarrowest', 'Udo najwęższe'],
    ['chest', 'Klatka piersiowa'],
    ['arm', 'Ramię']
  ]);

  form: UntypedFormGroup = new UntypedFormGroup({
    id: new UntypedFormControl(null),
    patientId: new UntypedFormControl(null),
    measurementDate: new UntypedFormControl(null, [Validators.required, CalendarDateValidator]),
    bodyWeight: new UntypedFormControl(null, [Validators.required]),
    breast: new UntypedFormControl(null),
    underBreast: new UntypedFormControl(null),
    waist: new UntypedFormControl(null),
    abdominal: new UntypedFormControl(null),
    hipBones: new UntypedFormControl(null),
    hips: new UntypedFormControl(null),
    thighWidest: new UntypedFormControl(null),
    thighNarrowest: new UntypedFormControl(null),
    calf: new UntypedFormControl(null),
    chest: new UntypedFormControl(null),
    arm: new UntypedFormControl(null),
  });

  initializeFormGroup() {
    this.clearForm();

    this.form.setValue({
      id: null,
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

  populateForm(measurement) {
    this.clearForm();
    this.form.setValue(measurement);
  }

  clearForm() {
    this.form.reset();
  }
}
