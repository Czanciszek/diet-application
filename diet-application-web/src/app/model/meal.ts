export interface Meal {
  id: string,
  name: string,
  dayMealId: string,
  foodId: string,
  isProduct: number,
  productList: any[],
  grams: number,
  portions: number,
  dishPortions: number,
  recipe: string,
  foodType: string
}
