import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
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

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    patientId: new FormControl(null),
    measurementDate: new FormControl(null, [Validators.required, CalendarDateValidator]),
    bodyWeight: new FormControl(null, [Validators.required]),
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
