export interface DayMeal {
  id: string,
  header: object,
  primaryImageId: string,
  type: string,
  name: string,
  menuDocRef: {
    id: string,
    name: string,
    primaryImageId: string,
    type: string
  },
  weekMealDocRef: {
    id: string,
    name: string,
    primaryImageId: string,
    type: string
  },
  date: string,
  mealList: any[],
  dayType: string,
}
