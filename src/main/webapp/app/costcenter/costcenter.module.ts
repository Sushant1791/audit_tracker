import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuditTrackerSharedModule } from 'app/shared';
import { COSTCENTRE_ROUTE, CostCentreComponent, CostCentreUpdateComponent } from './';

@NgModule({
    imports: [AuditTrackerSharedModule, RouterModule.forChild(COSTCENTRE_ROUTE)],
    declarations: [CostCentreComponent, CostCentreUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuditTrackerCostCentreModule {}
