export interface Menu {
  id: string,
  patientId: string,
  weekMealList: string[],
  foodTypes: string [],
  startDate: string,
  endDate: string,
  energyLimit: number,
  proteinsLimit: number,
  fatsLimit: number,
  carbohydratesLimit: number
}
