import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICollection } from 'app/shared/model/collection.model';

@Component({
  selector: 'jhi-collection-detail',
  templateUrl: './collection-detail.component.html'
})
export class CollectionDetailComponent implements OnInit {
  collection: ICollection;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ collection }) => {
      this.collection = collection;
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
