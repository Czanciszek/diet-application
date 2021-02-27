export interface Meal {
  id: string,
  header: object,
  primaryImageId: string,
  type: string,
  name: string,
  dayMealId: string,
  foodId: string,
  isProduct: boolean,
  productForDishList: object,
  portions: number,
  recipe: string,
  mealType: string
}
