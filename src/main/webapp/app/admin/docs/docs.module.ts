import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ShopGiaLinhSharedModule } from 'app/shared/shared.module';

import { JhiDocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [ShopGiaLinhSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [JhiDocsComponent]
})
export class DocsModule {}
