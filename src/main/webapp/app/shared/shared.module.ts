import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { AuditTrackerSharedLibsModule, AuditTrackerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { JhiBulkUpdatePlanModalComponent } from './auditplan/bulkupdate-plan.component';
import { JhiBulkUpdateTestingModalComponent } from './audit-testing/bulkupdate-testing.component';
import { JhiBulkUpdateEntityModalComponent } from './audit-entity/bulkupdate-entity.component';

@NgModule({
    imports: [AuditTrackerSharedLibsModule, AuditTrackerSharedCommonModule],
    declarations: [
        JhiLoginModalComponent,
        JhiBulkUpdatePlanModalComponent,
        JhiBulkUpdateEntityModalComponent,
        JhiBulkUpdateTestingModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [
        JhiLoginModalComponent,
        JhiBulkUpdateTestingModalComponent,
        JhiBulkUpdateEntityModalComponent,
        JhiBulkUpdatePlanModalComponent
    ],
    exports: [
        AuditTrackerSharedCommonModule,
        JhiLoginModalComponent,
        JhiBulkUpdateTestingModalComponent,
        JhiBulkUpdateEntityModalComponent,
        JhiBulkUpdatePlanModalComponent,
        HasAnyAuthorityDirective
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuditTrackerSharedModule {}
