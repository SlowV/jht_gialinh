import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrdersProduct } from 'app/shared/model/orders-product.model';
import { OrdersProductService } from './orders-product.service';

@Component({
  selector: 'jhi-orders-product-delete-dialog',
  templateUrl: './orders-product-delete-dialog.component.html'
})
export class OrdersProductDeleteDialogComponent {
  ordersProduct: IOrdersProduct;

  constructor(
    protected ordersProductService: OrdersProductService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ordersProductService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ordersProductListModification',
        content: 'Deleted an ordersProduct'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-orders-product-delete-popup',
  template: ''
})
export class OrdersProductDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ordersProduct }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrdersProductDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ordersProduct = ordersProduct;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/orders-product', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/orders-product', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
