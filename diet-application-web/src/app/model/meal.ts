export interface Meal {
  id: string,
  header: object,
  primaryImageId: string,
  type: string,
  name: string,
  dayMealId: string,
  foodId: string,
  isProduct: number,
  productForDishList: any[],
  grams: number,
  portions: number,
  recipe: string,
  mealType: string
}
