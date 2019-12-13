import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IAccounts {
  id?: number;
  email?: string;
  password?: string;
  fullName?: string;
  phone?: string;
  avatar?: string;
  gender?: number;
  address?: string;
  introduction?: any;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  status?: number;
  customers?: ICustomer[];
}

export class Accounts implements IAccounts {
  constructor(
    public id?: number,
    public email?: string,
    public password?: string,
    public fullName?: string,
    public phone?: string,
    public avatar?: string,
    public gender?: number,
    public address?: string,
    public introduction?: any,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public status?: number,
    public customers?: ICustomer[]
  ) {}
}
