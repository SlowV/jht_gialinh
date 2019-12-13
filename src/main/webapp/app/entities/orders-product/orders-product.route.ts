import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrdersProduct } from 'app/shared/model/orders-product.model';
import { OrdersProductService } from './orders-product.service';
import { OrdersProductComponent } from './orders-product.component';
import { OrdersProductDetailComponent } from './orders-product-detail.component';
import { OrdersProductUpdateComponent } from './orders-product-update.component';
import { OrdersProductDeletePopupComponent } from './orders-product-delete-dialog.component';
import { IOrdersProduct } from 'app/shared/model/orders-product.model';

@Injectable({ providedIn: 'root' })
export class OrdersProductResolve implements Resolve<IOrdersProduct> {
  constructor(private service: OrdersProductService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrdersProduct> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrdersProduct>) => response.ok),
        map((ordersProduct: HttpResponse<OrdersProduct>) => ordersProduct.body)
      );
    }
    return of(new OrdersProduct());
  }
}

export const ordersProductRoute: Routes = [
  {
    path: '',
    component: OrdersProductComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'shopGiaLinhApp.ordersProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrdersProductDetailComponent,
    resolve: {
      ordersProduct: OrdersProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.ordersProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrdersProductUpdateComponent,
    resolve: {
      ordersProduct: OrdersProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.ordersProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrdersProductUpdateComponent,
    resolve: {
      ordersProduct: OrdersProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.ordersProduct.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ordersProductPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrdersProductDeletePopupComponent,
    resolve: {
      ordersProduct: OrdersProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'shopGiaLinhApp.ordersProduct.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
