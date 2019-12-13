import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.ShopGiaLinhCategoryModule)
      },
      {
        path: 'collection',
        loadChildren: () => import('./collection/collection.module').then(m => m.ShopGiaLinhCollectionModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.ShopGiaLinhProductModule)
      },
      {
        path: 'accounts',
        loadChildren: () => import('./accounts/accounts.module').then(m => m.ShopGiaLinhAccountsModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.ShopGiaLinhCustomerModule)
      },
      {
        path: 'orders',
        loadChildren: () => import('./orders/orders.module').then(m => m.ShopGiaLinhOrdersModule)
      },
      {
        path: 'orders-product',
        loadChildren: () => import('./orders-product/orders-product.module').then(m => m.ShopGiaLinhOrdersProductModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ShopGiaLinhEntityModule {}
