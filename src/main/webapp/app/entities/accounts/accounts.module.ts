import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShopGiaLinhSharedModule } from 'app/shared/shared.module';
import { AccountsComponent } from './accounts.component';
import { AccountsDetailComponent } from './accounts-detail.component';
import { AccountsUpdateComponent } from './accounts-update.component';
import { AccountsDeletePopupComponent, AccountsDeleteDialogComponent } from './accounts-delete-dialog.component';
import { accountsRoute, accountsPopupRoute } from './accounts.route';

const ENTITY_STATES = [...accountsRoute, ...accountsPopupRoute];

@NgModule({
  imports: [ShopGiaLinhSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountsComponent,
    AccountsDetailComponent,
    AccountsUpdateComponent,
    AccountsDeleteDialogComponent,
    AccountsDeletePopupComponent
  ],
  entryComponents: [AccountsDeleteDialogComponent]
})
export class ShopGiaLinhAccountsModule {}
