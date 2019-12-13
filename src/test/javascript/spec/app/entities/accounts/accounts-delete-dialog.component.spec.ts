import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShopGiaLinhTestModule } from '../../../test.module';
import { AccountsDeleteDialogComponent } from 'app/entities/accounts/accounts-delete-dialog.component';
import { AccountsService } from 'app/entities/accounts/accounts.service';

describe('Component Tests', () => {
  describe('Accounts Management Delete Component', () => {
    let comp: AccountsDeleteDialogComponent;
    let fixture: ComponentFixture<AccountsDeleteDialogComponent>;
    let service: AccountsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShopGiaLinhTestModule],
        declarations: [AccountsDeleteDialogComponent]
      })
        .overrideTemplate(AccountsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountsService);
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
