import { IOrders } from 'app/shared/model/orders.model';

export interface ICustomer {
  id?: number;
  name?: string;
  shipEmail?: string;
  shipPhone?: string;
  shipAddress?: string;
  orders?: IOrders[];
  accountsId?: number;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public shipEmail?: string,
    public shipPhone?: string,
    public shipAddress?: string,
    public orders?: IOrders[],
    public accountsId?: number
  ) {}
}
