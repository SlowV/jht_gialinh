import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { ICollection } from 'app/shared/model/collection.model';
import { CollectionService } from 'app/entities/collection/collection.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving: boolean;

  categories: ICategory[];

  collections: ICollection[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    price: [],
    description: [],
    images: [],
    isSale: [],
    percent: [],
    detail: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: [],
    status: [],
    categoryId: [],
    collectionId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productService: ProductService,
    protected categoryService: CategoryService,
    protected collectionService: CollectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
    this.categoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategory[]>) => response.body)
      )
      .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.collectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICollection[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICollection[]>) => response.body)
      )
      .subscribe((res: ICollection[]) => (this.collections = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(product: IProduct) {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      price: product.price,
      description: product.description,
      images: product.images,
      isSale: product.isSale,
      percent: product.percent,
      detail: product.detail,
      createdAt: product.createdAt != null ? product.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: product.updatedAt != null ? product.updatedAt.format(DATE_TIME_FORMAT) : null,
      deletedAt: product.deletedAt != null ? product.deletedAt.format(DATE_TIME_FORMAT) : null,
      status: product.status,
      categoryId: product.categoryId,
      collectionId: product.collectionId
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
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      price: this.editForm.get(['price']).value,
      description: this.editForm.get(['description']).value,
      images: this.editForm.get(['images']).value,
      isSale: this.editForm.get(['isSale']).value,
      percent: this.editForm.get(['percent']).value,
      detail: this.editForm.get(['detail']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      deletedAt:
        this.editForm.get(['deletedAt']).value != null ? moment(this.editForm.get(['deletedAt']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      categoryId: this.editForm.get(['categoryId']).value,
      collectionId: this.editForm.get(['collectionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
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

  trackCategoryById(index: number, item: ICategory) {
    return item.id;
  }

  trackCollectionById(index: number, item: ICollection) {
    return item.id;
  }
}
