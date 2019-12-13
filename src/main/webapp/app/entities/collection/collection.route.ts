import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Collection } from 'app/shared/model/collection.model';
import { CollectionService } from './collection.service';
import { CollectionComponent } from './collection.component';
import { CollectionDetailComponent } from './collection-detail.component';
import { CollectionUpdateComponent } from './collection-update.component';
import { CollectionDeletePopupComponent } from './collection-delete-dialog.component';
import { ICollection } from 'app/shared/model/collection.model';

@Injectable({ providedIn: 'root' })
export class CollectionResolve implements Resolve<ICollection> {
  constructor(private service: CollectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICollection> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Collection>) => response.ok),
        map((collection: HttpResponse<Collection>) => collection.body)
      );
    }
    return of(new Collection());
  }
}

export const collectionRoute: Routes = [
  {
    path: '',
    component: CollectionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'shopGiaLinhApp.collection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CollectionDetailComponent,
    resolve: {
      collection: CollectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.collection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CollectionUpdateComponent,
    resolve: {
      collection: CollectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.collection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CollectionUpdateComponent,
    resolve: {
      collection: CollectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.collection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const collectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CollectionDeletePopupComponent,
    resolve: {
      collection: CollectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.collection.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
