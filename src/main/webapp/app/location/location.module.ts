import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuditTrackerSharedModule } from 'app/shared';
import { LOCATION_ROUTE, LocationComponent, LocationUpdateComponent } from './';

@NgModule({
    imports: [AuditTrackerSharedModule, RouterModule.forChild(LOCATION_ROUTE)],
    declarations: [LocationComponent, LocationUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuditTrackerLocationModule {}
