import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrdersProduct } from 'app/shared/model/orders-product.model';

type EntityResponseType = HttpResponse<IOrdersProduct>;
type EntityArrayResponseType = HttpResponse<IOrdersProduct[]>;

@Injectable({ providedIn: 'root' })
export class OrdersProductService {
  public resourceUrl = SERVER_API_URL + 'api/orders-products';

  constructor(protected http: HttpClient) {}

  create(ordersProduct: IOrdersProduct): Observable<EntityResponseType> {
    return this.http.post<IOrdersProduct>(this.resourceUrl, ordersProduct, { observe: 'response' });
  }

  update(ordersProduct: IOrdersProduct): Observable<EntityResponseType> {
    return this.http.put<IOrdersProduct>(this.resourceUrl, ordersProduct, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrdersProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrdersProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
