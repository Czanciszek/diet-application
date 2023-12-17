import { Meal } from "./meal";

export interface WeekMeal {
  id: string,
  menu: string,
  patient: string,
  meals: Map<string, Meal[]>
}
