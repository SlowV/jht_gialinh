import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICollection } from 'app/shared/model/collection.model';

type EntityResponseType = HttpResponse<ICollection>;
type EntityArrayResponseType = HttpResponse<ICollection[]>;

@Injectable({ providedIn: 'root' })
export class CollectionService {
  public resourceUrl = SERVER_API_URL + 'api/collections';

  constructor(protected http: HttpClient) {}

  create(collection: ICollection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collection);
    return this.http
      .post<ICollection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(collection: ICollection): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collection);
    return this.http
      .put<ICollection>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICollection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICollection[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(collection: ICollection): ICollection {
    const copy: ICollection = Object.assign({}, collection, {
      createdAt: collection.createdAt != null && collection.createdAt.isValid() ? collection.createdAt.toJSON() : null,
      updatedAt: collection.updatedAt != null && collection.updatedAt.isValid() ? collection.updatedAt.toJSON() : null,
      deletedAt: collection.deletedAt != null && collection.deletedAt.isValid() ? collection.deletedAt.toJSON() : null
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
      res.body.forEach((collection: ICollection) => {
        collection.createdAt = collection.createdAt != null ? moment(collection.createdAt) : null;
        collection.updatedAt = collection.updatedAt != null ? moment(collection.updatedAt) : null;
        collection.deletedAt = collection.deletedAt != null ? moment(collection.deletedAt) : null;
      });
    }
    return res;
  }
}
