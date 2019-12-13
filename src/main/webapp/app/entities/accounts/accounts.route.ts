import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Accounts } from 'app/shared/model/accounts.model';
import { AccountsService } from './accounts.service';
import { AccountsComponent } from './accounts.component';
import { AccountsDetailComponent } from './accounts-detail.component';
import { AccountsUpdateComponent } from './accounts-update.component';
import { AccountsDeletePopupComponent } from './accounts-delete-dialog.component';
import { IAccounts } from 'app/shared/model/accounts.model';

@Injectable({ providedIn: 'root' })
export class AccountsResolve implements Resolve<IAccounts> {
  constructor(private service: AccountsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccounts> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Accounts>) => response.ok),
        map((accounts: HttpResponse<Accounts>) => accounts.body)
      );
    }
    return of(new Accounts());
  }
}

export const accountsRoute: Routes = [
  {
    path: '',
    component: AccountsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'shopGiaLinhApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountsDetailComponent,
    resolve: {
      accounts: AccountsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountsUpdateComponent,
    resolve: {
      accounts: AccountsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountsUpdateComponent,
    resolve: {
      accounts: AccountsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountsDeletePopupComponent,
    resolve: {
      accounts: AccountsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
