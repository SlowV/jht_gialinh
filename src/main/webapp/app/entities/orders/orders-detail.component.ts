import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOrders } from 'app/shared/model/orders.model';

@Component({
  selector: 'jhi-orders-detail',
  templateUrl: './orders-detail.component.html'
})
export class OrdersDetailComponent implements OnInit {
  orders: IOrders;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orders }) => {
      this.orders = orders;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
