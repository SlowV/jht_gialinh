import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOrdersProduct, OrdersProduct } from 'app/shared/model/orders-product.model';
import { OrdersProductService } from './orders-product.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-orders-product-update',
  templateUrl: './orders-product-update.component.html'
})
export class OrdersProductUpdateComponent implements OnInit {
  isSaving: boolean;

  orders: IOrders[];

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    quantity: [],
    unitPrice: [],
    ordersId: [],
    productId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ordersProductService: OrdersProductService,
    protected ordersService: OrdersService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ordersProduct }) => {
      this.updateForm(ordersProduct);
    });
    this.ordersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrders[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrders[]>) => response.body)
      )
      .subscribe((res: IOrders[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ordersProduct: IOrdersProduct) {
    this.editForm.patchValue({
      id: ordersProduct.id,
      quantity: ordersProduct.quantity,
      unitPrice: ordersProduct.unitPrice,
      ordersId: ordersProduct.ordersId,
      productId: ordersProduct.productId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ordersProduct = this.createFromForm();
    if (ordersProduct.id !== undefined) {
      this.subscribeToSaveResponse(this.ordersProductService.update(ordersProduct));
    } else {
      this.subscribeToSaveResponse(this.ordersProductService.create(ordersProduct));
    }
  }

  private createFromForm(): IOrdersProduct {
    return {
      ...new OrdersProduct(),
      id: this.editForm.get(['id']).value,
      quantity: this.editForm.get(['quantity']).value,
      unitPrice: this.editForm.get(['unitPrice']).value,
      ordersId: this.editForm.get(['ordersId']).value,
      productId: this.editForm.get(['productId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdersProduct>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOrdersById(index: number, item: IOrders) {
    return item.id;
  }

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }
}
