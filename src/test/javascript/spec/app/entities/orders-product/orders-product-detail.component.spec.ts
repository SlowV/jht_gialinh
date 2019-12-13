import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShopGiaLinhTestModule } from '../../../test.module';
import { OrdersProductDetailComponent } from 'app/entities/orders-product/orders-product-detail.component';
import { OrdersProduct } from 'app/shared/model/orders-product.model';

describe('Component Tests', () => {
  describe('OrdersProduct Management Detail Component', () => {
    let comp: OrdersProductDetailComponent;
    let fixture: ComponentFixture<OrdersProductDetailComponent>;
    const route = ({ data: of({ ordersProduct: new OrdersProduct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShopGiaLinhTestModule],
        declarations: [OrdersProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrdersProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrdersProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ordersProduct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
