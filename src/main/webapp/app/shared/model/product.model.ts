import { Moment } from 'moment';
import { IOrdersProduct } from 'app/shared/model/orders-product.model';

export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  description?: string;
  images?: string;
  isSale?: boolean;
  percent?: number;
  detail?: any;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  status?: number;
  ordersProducts?: IOrdersProduct[];
  categoryId?: number;
  collectionId?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public description?: string,
    public images?: string,
    public isSale?: boolean,
    public percent?: number,
    public detail?: any,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public status?: number,
    public ordersProducts?: IOrdersProduct[],
    public categoryId?: number,
    public collectionId?: number
  ) {
    this.isSale = this.isSale || false;
  }
}
