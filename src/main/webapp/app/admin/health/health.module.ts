import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ShopGiaLinhSharedModule } from 'app/shared/shared.module';

import { JhiHealthCheckComponent } from './health.component';
import { JhiHealthModalComponent } from './health-modal.component';

import { healthRoute } from './health.route';

@NgModule({
  imports: [ShopGiaLinhSharedModule, RouterModule.forChild([healthRoute])],
  declarations: [JhiHealthCheckComponent, JhiHealthModalComponent],
  entryComponents: [JhiHealthModalComponent]
})
export class HealthModule {}
