export interface Menu {
  id: string,
  patientId: string,
  measurementId: string,
  weekMealList: string[],
  mealTypes:string [],
  startDate: string,
  endDate: string,
  activityLevel: number
}
