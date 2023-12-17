export class ProductCategory {

  constructor(
    readonly category: string,
    readonly subcategory: string
  ) {
    this.category = category;
    this.subcategory = subcategory;
  }
}

export class ProductCategorySet {
  private items: ProductCategory[] = [];

  constructor(items: ProductCategory[]) {
    this.addAll(items);
  }

  addAll(items: ProductCategory[]): void {
    for (const item of items) {
      this.add(item);
    }
  }

  add(item: ProductCategory): void {
    if (!this.has(item)) {
      this.items.push(item);
    }
  }

  values(): ProductCategory[] {
    return [...this.items];
  }

  private has(item: ProductCategory): boolean {
    return this.items.some(existing => existing.category === item.category && existing.subcategory === item.subcategory);
  }

}
