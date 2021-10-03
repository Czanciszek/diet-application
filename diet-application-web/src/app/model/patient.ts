export interface Patient {
  id: string,
  name: string,
  birthDate: string,
  numberPhone: string,
  email: string,
  sex: boolean,
  bodyHeight: number,
  currentLifestyleNote: string,
  changedLifestyleNote: string,
  dietaryPurpose: string,
  measurements: any,
  allergens: string[],
  unlikelyCategories: string[]
}
