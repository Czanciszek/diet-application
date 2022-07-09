import {DayMeal} from "./day-meal";

export interface Menu {
  id: string,
  patientId: string,
  weekMealList: string[],
  dayMealTypeList: DayMeal[],
  foodTypes: string [],
  startDate: string,
  endDate: string,
  recommendations: string,
  energyLimit: number,
  proteinsLimit: number,
  fatsLimit: number,
  carbohydratesLimit: number
}
