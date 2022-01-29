export interface Meal {
  id: string,
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
