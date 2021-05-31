export interface Dish {
  id: string,
  name: string,
  dayMealId: string,
  products: any[],
  portions: number,
  recipe: string,
  foodType: string
}
