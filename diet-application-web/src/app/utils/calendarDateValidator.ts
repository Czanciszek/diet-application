import { FormControl } from "@angular/forms";

export var CalendarDateValidator = (control: FormControl) => {
  if (control.value instanceof Date) {
    const date = control.value;
    const year = date.getFullYear() + "";
    const month = (date.getMonth() + 1) + "";
    const day = date.getDate() + "";
    let dateString = year + "-" + month.padStart(2, '0') + "-" + day.padStart(2, '0');
    control.setValue(dateString);
  }

  return control.value ? null : { invalidDate: true };
};
