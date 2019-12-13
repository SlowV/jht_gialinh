import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShopGiaLinhTestModule } from '../../../test.module';
import { OrdersProductUpdateComponent } from 'app/entities/orders-product/orders-product-update.component';
import { OrdersProductService } from 'app/entities/orders-product/orders-product.service';
import { OrdersProduct } from 'app/shared/model/orders-product.model';

describe('Component Tests', () => {
  describe('OrdersProduct Management Update Component', () => {
    let comp: OrdersProductUpdateComponent;
    let fixture: ComponentFixture<OrdersProductUpdateComponent>;
    let service: OrdersProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShopGiaLinhTestModule],
        declarations: [OrdersProductUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrdersProductUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdersProductUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrdersProductService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrdersProduct(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrdersProduct();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
