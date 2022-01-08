export interface Dish {
  id: string,
  name: string,
  menuId: string,
  products: any[],
  portions: number,
  recipe: string,
  foodType: string
}
