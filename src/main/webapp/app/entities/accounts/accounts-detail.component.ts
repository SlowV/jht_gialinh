import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAccounts } from 'app/shared/model/accounts.model';

@Component({
  selector: 'jhi-accounts-detail',
  templateUrl: './accounts-detail.component.html'
})
export class AccountsDetailComponent implements OnInit {
  accounts: IAccounts;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accounts }) => {
      this.accounts = accounts;
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
