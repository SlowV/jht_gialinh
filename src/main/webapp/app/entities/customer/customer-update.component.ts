import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IAccounts } from 'app/shared/model/accounts.model';
import { AccountsService } from 'app/entities/accounts/accounts.service';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving: boolean;

  accounts: IAccounts[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    shipEmail: [null, [Validators.required]],
    shipPhone: [null, [Validators.required]],
    shipAddress: [null, [Validators.required]],
    accountsId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerService: CustomerService,
    protected accountsService: AccountsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
    this.accountsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAccounts[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAccounts[]>) => response.body)
      )
      .subscribe((res: IAccounts[]) => (this.accounts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customer: ICustomer) {
    this.editForm.patchValue({
      id: customer.id,
      name: customer.name,
      shipEmail: customer.shipEmail,
      shipPhone: customer.shipPhone,
      shipAddress: customer.shipAddress,
      accountsId: customer.accountsId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      shipEmail: this.editForm.get(['shipEmail']).value,
      shipPhone: this.editForm.get(['shipPhone']).value,
      shipAddress: this.editForm.get(['shipAddress']).value,
      accountsId: this.editForm.get(['accountsId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
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

  trackAccountsById(index: number, item: IAccounts) {
    return item.id;
  }
}
