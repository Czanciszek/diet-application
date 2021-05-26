export interface Menu {
  id: string,
  patientId: string,
  measurementId: string,
  weekMealList: string[],
  foodPropertiesType: {},
  foodTypes: string [],
  startDate: string,
  endDate: string,
  activityLevel: number
}
