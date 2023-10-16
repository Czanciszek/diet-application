import { ProductCategory } from "./product-category";

export class Product {
  id: string;
  name: string;
  category: ProductCategory;
  foodProperties: {
    energyValue: number;
    proteins: number;
    fats: number;
    saturatedFattyAcids: number;
    monoUnsaturatedFattyAcids: number;
    polyUnsaturatedFattyAcids: number;
    cholesterol: number;
    carbohydrates: number;
    sucrose: number;
    dietaryFibres: number;
    sodium: number;
    potassium: number;
    calcium: number;
    phosphorus: number;
    magnesium: number;
    iron: number;
    selenium: number;
    betaCarotene: number;
    vitaminD: number;
    vitaminC: number;
  };
  amountTypes: any[];
  allergenTypes: any[];

  constructor(
    id: string,
    name: string,
    category: any,
    foodProperties: any,
    amountTypes: any[],
    allergenTypes: any[]
  ) {
    this.id = id;
    this.name = name;
    this.category = new ProductCategory(category.category, category.subcategory);
    this.foodProperties = foodProperties;
    this.amountTypes = amountTypes;
    this.allergenTypes = allergenTypes;
  }
}
