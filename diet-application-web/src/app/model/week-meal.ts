export interface WeekMeal {
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
  dayMealList: string[],
}
