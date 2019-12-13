import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccounts } from 'app/shared/model/accounts.model';

type EntityResponseType = HttpResponse<IAccounts>;
type EntityArrayResponseType = HttpResponse<IAccounts[]>;

@Injectable({ providedIn: 'root' })
export class AccountsService {
  public resourceUrl = SERVER_API_URL + 'api/accounts';

  constructor(protected http: HttpClient) {}

  create(accounts: IAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accounts);
    return this.http
      .post<IAccounts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accounts: IAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accounts);
    return this.http
      .put<IAccounts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccounts>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccounts[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(accounts: IAccounts): IAccounts {
    const copy: IAccounts = Object.assign({}, accounts, {
      createdAt: accounts.createdAt != null && accounts.createdAt.isValid() ? accounts.createdAt.toJSON() : null,
      updatedAt: accounts.updatedAt != null && accounts.updatedAt.isValid() ? accounts.updatedAt.toJSON() : null,
      deletedAt: accounts.deletedAt != null && accounts.deletedAt.isValid() ? accounts.deletedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
      res.body.deletedAt = res.body.deletedAt != null ? moment(res.body.deletedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accounts: IAccounts) => {
        accounts.createdAt = accounts.createdAt != null ? moment(accounts.createdAt) : null;
        accounts.updatedAt = accounts.updatedAt != null ? moment(accounts.updatedAt) : null;
        accounts.deletedAt = accounts.deletedAt != null ? moment(accounts.deletedAt) : null;
      });
    }
    return res;
  }
}
