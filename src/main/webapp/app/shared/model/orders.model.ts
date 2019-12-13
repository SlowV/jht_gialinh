import { Moment } from 'moment';
import { IOrdersProduct } from 'app/shared/model/orders-product.model';

export interface IOrders {
  id?: number;
  totalPrice?: number;
  note?: any;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  status?: number;
  ordersProducts?: IOrdersProduct[];
  customerId?: number;
}

export class Orders implements IOrders {
  constructor(
    public id?: number,
    public totalPrice?: number,
    public note?: any,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public status?: number,
    public ordersProducts?: IOrdersProduct[],
    public customerId?: number
  ) {}
}
