import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShopGiaLinhSharedModule } from 'app/shared/shared.module';
import { CollectionComponent } from './collection.component';
import { CollectionDetailComponent } from './collection-detail.component';
import { CollectionUpdateComponent } from './collection-update.component';
import { CollectionDeletePopupComponent, CollectionDeleteDialogComponent } from './collection-delete-dialog.component';
import { collectionRoute, collectionPopupRoute } from './collection.route';

const ENTITY_STATES = [...collectionRoute, ...collectionPopupRoute];

@NgModule({
  imports: [ShopGiaLinhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CollectionComponent,
    CollectionDetailComponent,
    CollectionUpdateComponent,
    CollectionDeleteDialogComponent,
    CollectionDeletePopupComponent
  ],
  entryComponents: [CollectionDeleteDialogComponent]
})
export class ShopGiaLinhCollectionModule {}
