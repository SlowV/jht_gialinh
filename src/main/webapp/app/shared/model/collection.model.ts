import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';

export interface ICollection {
  id?: number;
  name?: string;
  description?: any;
  images?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  status?: number;
  products?: IProduct[];
}

export class Collection implements ICollection {
  constructor(
    public id?: number,
    public name?: string,
    public description?: any,
    public images?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public status?: number,
    public products?: IProduct[]
  ) {}
}
