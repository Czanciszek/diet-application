export class Dish {
  id: string;
  name: string;
  products: DishProduct[];
  portions: number;
  recipe: string;
  foodType: string

  constructor(
    id: string,
    name: string,
    products: DishProduct[],
    portions: number,
    recipe: string,
    foodType: string
  ) {
    this.id = id;
    this.name = name;
    this.products = products.map(dishProductData => {
      return new DishProduct(
        dishProductData['productId'],
        dishProductData['productName'],
        dishProductData['amount'],
        dishProductData['amountType'],
        dishProductData['amountTypes'],
        dishProductData['grams'],
      )
    });;
    this.portions = portions;
    this.recipe = recipe;
    this.foodType = foodType;
  }
}

export class DishProduct {
  productId: string;
  productName: string;
  amount: number;
  amountType: string;
  amountTypes: any[];
  grams: number;

  constructor(
    productId: string,
    productName: string,
    amount: number,
    amountType: string,
    amountTypes: any[],
    grams: number
  ) {
    this.productId = productId;
    this.productName = productName;
    this.amount = amount;
    this.amountType = amountType;
    this.amountTypes = amountTypes;
    this.grams = grams;
  }
}
