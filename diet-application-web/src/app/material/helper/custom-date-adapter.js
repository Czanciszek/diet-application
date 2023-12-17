import { NativeDateAdapter } from "@angular/material/core";

/** Adapts the native JS Date for use with cdk-based components that work with dates. */
export class CustomDateAdapter extends NativeDateAdapter {

  // parse the date from input component as it only expect dates in
  // dd-mm-yyyy format
  parse(value) {
    if (typeof value === 'string') {
      if (value.indexOf('/') > -1) {
        return this.parseValue(value, '/');
      } else if (value.indexOf('.') > -1) {
        return this.parseValue(value, '.');
      } else if (value.indexOf('-') > -1) {
        return this.parseValue(value, '-');
      }
    }

    return null;
  }

  parseValue(value, character) {
    const str = value.split(character);

    const year = Number(str[2]);
    const month = Number(str[1]) - 1;
    const day = Number(str[0]);

    return new Date(year, month, day);
  }

  getFirstDayOfWeek() {
   return 1;
  }

}
