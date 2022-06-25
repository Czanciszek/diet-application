export interface Meal {
  id: number,
  name: string,
  dayMealId: string,
  originMealId: number,
  foodId: string,
  isProduct: number,
  productList: any[],
  grams: number,
  portions: number,
  dishPortions: number,
  recipe: string,
  foodType: string
}
