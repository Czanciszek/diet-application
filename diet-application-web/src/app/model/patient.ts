export interface Patient {
  id: string,
  name: string,
  surname: string,
  birthDate: string,
  numberPhone: string,
  email: string,
  sex: boolean,
  bodyHeight: number,
  currentLifestyleNote: string,
  changedLifestyleNote: string,
  dietaryPurpose: string,
  allergens: string[],
  unlikelyCategories: string[]
}
