import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollection } from 'app/shared/model/collection.model';
import { CollectionService } from './collection.service';

@Component({
  selector: 'jhi-collection-delete-dialog',
  templateUrl: './collection-delete-dialog.component.html'
})
export class CollectionDeleteDialogComponent {
  collection: ICollection;

  constructor(
    protected collectionService: CollectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.collectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'collectionListModification',
        content: 'Deleted an collection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-collection-delete-popup',
  template: ''
})
export class CollectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ collection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CollectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.collection = collection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/collection', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/collection', { outlets: { popup: null } }]);
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
