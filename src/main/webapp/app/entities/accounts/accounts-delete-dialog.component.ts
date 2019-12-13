import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccounts } from 'app/shared/model/accounts.model';
import { AccountsService } from './accounts.service';

@Component({
  selector: 'jhi-accounts-delete-dialog',
  templateUrl: './accounts-delete-dialog.component.html'
})
export class AccountsDeleteDialogComponent {
  accounts: IAccounts;

  constructor(protected accountsService: AccountsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'accountsListModification',
        content: 'Deleted an accounts'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-accounts-delete-popup',
  template: ''
})
export class AccountsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accounts }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AccountsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.accounts = accounts;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/accounts', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/accounts', { outlets: { popup: null } }]);
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
