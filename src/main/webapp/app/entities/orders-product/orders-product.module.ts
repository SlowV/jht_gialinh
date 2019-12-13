import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShopGiaLinhSharedModule } from 'app/shared/shared.module';
import { OrdersProductComponent } from './orders-product.component';
import { OrdersProductDetailComponent } from './orders-product-detail.component';
import { OrdersProductUpdateComponent } from './orders-product-update.component';
import { OrdersProductDeletePopupComponent, OrdersProductDeleteDialogComponent } from './orders-product-delete-dialog.component';
import { ordersProductRoute, ordersProductPopupRoute } from './orders-product.route';

const ENTITY_STATES = [...ordersProductRoute, ...ordersProductPopupRoute];

@NgModule({
  imports: [ShopGiaLinhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrdersProductComponent,
    OrdersProductDetailComponent,
    OrdersProductUpdateComponent,
    OrdersProductDeleteDialogComponent,
    OrdersProductDeletePopupComponent
  ],
  entryComponents: [OrdersProductDeleteDialogComponent]
})
export class ShopGiaLinhOrdersProductModule {}
