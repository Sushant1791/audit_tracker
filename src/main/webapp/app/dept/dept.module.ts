import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuditTrackerSharedModule } from 'app/shared';
import { DEPT_ROUTE, DeptComponent, DeptUpdateComponent } from './';

@NgModule({
    imports: [AuditTrackerSharedModule, RouterModule.forChild(DEPT_ROUTE)],
    declarations: [DeptComponent, DeptUpdateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuditTrackerDeptModule {}
