export interface Menu {
  id: string,
  header: object,
  primaryImageId: string,
  type: string,
  name: string,
  patientId: string,
  measurementId: string,
  weekMealList: string[],
  mealTypes:string [],
  startDate: string,
  endDate: string,
  activityLevel: number
}
