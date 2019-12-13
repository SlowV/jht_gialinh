import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShopGiaLinhTestModule } from '../../../test.module';
import { OrdersProductDeleteDialogComponent } from 'app/entities/orders-product/orders-product-delete-dialog.component';
import { OrdersProductService } from 'app/entities/orders-product/orders-product.service';

describe('Component Tests', () => {
  describe('OrdersProduct Management Delete Component', () => {
    let comp: OrdersProductDeleteDialogComponent;
    let fixture: ComponentFixture<OrdersProductDeleteDialogComponent>;
    let service: OrdersProductService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShopGiaLinhTestModule],
        declarations: [OrdersProductDeleteDialogComponent]
      })
        .overrideTemplate(OrdersProductDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrdersProductDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrdersProductService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
