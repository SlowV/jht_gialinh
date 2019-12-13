export interface IOrdersProduct {
  id?: number;
  quantity?: number;
  unitPrice?: number;
  ordersId?: number;
  productId?: number;
}

export class OrdersProduct implements IOrdersProduct {
  constructor(
    public id?: number,
    public quantity?: number,
    public unitPrice?: number,
    public ordersId?: number,
    public productId?: number
  ) {}
}
