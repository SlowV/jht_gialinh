import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrdersProduct } from 'app/shared/model/orders-product.model';

@Component({
  selector: 'jhi-orders-product-detail',
  templateUrl: './orders-product-detail.component.html'
})
export class OrdersProductDetailComponent implements OnInit {
  ordersProduct: IOrdersProduct;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ordersProduct }) => {
      this.ordersProduct = ordersProduct;
    });
  }

  previousState() {
    window.history.back();
  }
}
