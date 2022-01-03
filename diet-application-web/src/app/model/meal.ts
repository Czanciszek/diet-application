export interface Meal {
  id: string,
  name: string,
  dayMealId: string,
  foodId: string,
  dishIdReference: number,
  isProduct: number,
  productList: any[],
  grams: number,
  portions: number,
  dishPortions: number,
  recipe: string,
  foodType: string
}
