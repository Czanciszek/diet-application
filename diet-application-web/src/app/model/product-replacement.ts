export class ProductReplacement {
  oldProduct: {
    id: number,
    name: string
  }

  newProduct: {
    id: number,
    name: string
  }

  constructor(oldProduct, newProduct) {
    this.oldProduct = { id: oldProduct.productId, name: oldProduct.productName};
    this.newProduct = { id: newProduct.id, name: newProduct.name };
  }
}
