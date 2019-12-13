import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShopGiaLinhTestModule } from '../../../test.module';
import { CollectionDetailComponent } from 'app/entities/collection/collection-detail.component';
import { Collection } from 'app/shared/model/collection.model';

describe('Component Tests', () => {
  describe('Collection Management Detail Component', () => {
    let comp: CollectionDetailComponent;
    let fixture: ComponentFixture<CollectionDetailComponent>;
    const route = ({ data: of({ collection: new Collection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShopGiaLinhTestModule],
        declarations: [CollectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CollectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
