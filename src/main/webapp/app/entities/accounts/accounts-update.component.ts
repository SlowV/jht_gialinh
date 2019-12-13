import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAccounts, Accounts } from 'app/shared/model/accounts.model';
import { AccountsService } from './accounts.service';

@Component({
  selector: 'jhi-accounts-update',
  templateUrl: './accounts-update.component.html'
})
export class AccountsUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    email: [null, [Validators.required]],
    password: [null, [Validators.required, Validators.minLength(6), Validators.maxLength(30)]],
    fullName: [null, [Validators.required]],
    phone: [null, [Validators.required]],
    avatar: [],
    gender: [],
    address: [null, [Validators.required]],
    introduction: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: [],
    status: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected accountsService: AccountsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accounts }) => {
      this.updateForm(accounts);
    });
  }

  updateForm(accounts: IAccounts) {
    this.editForm.patchValue({
      id: accounts.id,
      email: accounts.email,
      password: accounts.password,
      fullName: accounts.fullName,
      phone: accounts.phone,
      avatar: accounts.avatar,
      gender: accounts.gender,
      address: accounts.address,
      introduction: accounts.introduction,
      createdAt: accounts.createdAt != null ? accounts.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: accounts.updatedAt != null ? accounts.updatedAt.format(DATE_TIME_FORMAT) : null,
      deletedAt: accounts.deletedAt != null ? accounts.deletedAt.format(DATE_TIME_FORMAT) : null,
      status: accounts.status
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accounts = this.createFromForm();
    if (accounts.id !== undefined) {
      this.subscribeToSaveResponse(this.accountsService.update(accounts));
    } else {
      this.subscribeToSaveResponse(this.accountsService.create(accounts));
    }
  }

  private createFromForm(): IAccounts {
    return {
      ...new Accounts(),
      id: this.editForm.get(['id']).value,
      email: this.editForm.get(['email']).value,
      password: this.editForm.get(['password']).value,
      fullName: this.editForm.get(['fullName']).value,
      phone: this.editForm.get(['phone']).value,
      avatar: this.editForm.get(['avatar']).value,
      gender: this.editForm.get(['gender']).value,
      address: this.editForm.get(['address']).value,
      introduction: this.editForm.get(['introduction']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      deletedAt:
        this.editForm.get(['deletedAt']).value != null ? moment(this.editForm.get(['deletedAt']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccounts>>) {
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
}
