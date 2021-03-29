export interface Menu {
  id: string,
  header: object,
  primaryImageId: string,
  type: string,
  name: string,
  patientDocRef: {
    id: string,
    name: string,
    primaryImageId: string,
    type: string
  },
  measurementDocRef: {
    id: string,
    name: string,
    primaryImageId: string,
    type: string
  },
  weekMealList: string[],
  mealTypes:string [],
  startDate: string,
  endDate: string,
  activityLevel: number
}
