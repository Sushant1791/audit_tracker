import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuditTrackerSharedModule } from 'app/shared';

import { AuditPlanUpdateComponent, AuditPlanComponent, AuditProcedureComponent, auditState } from './';
import { AuditProcedureUpdateComponent } from './procedure/auditprocedure-update.component';
import { AuditProcedureObjectiveComponent } from './procedure/auditprocedure-objective.component';
import { AuditTestingComponent } from './testing/audittesting.component';
import { AuditTestingCreateComponent } from '../audit/testing/audittesting-create.component';
import { AuditeeUpdateComponent } from './auditee/auditee-update.component';
import { AuditeeWindowComponent } from './auditee/auditee-window.component';
import { AuditReportComponent } from './report/audit-report.component';
import { AuditReportGenerateComponent } from './report/audit-report-generate.component';

@NgModule({
    imports: [AuditTrackerSharedModule, RouterModule.forChild(auditState)],
    declarations: [
        AuditPlanUpdateComponent,
        AuditPlanComponent,
        AuditProcedureComponent,
        AuditProcedureUpdateComponent,
        AuditProcedureObjectiveComponent,
        AuditTestingComponent,
        AuditTestingCreateComponent,
        AuditeeUpdateComponent,
        AuditeeWindowComponent,
        AuditReportComponent,
        AuditReportGenerateComponent
    ],
    schemas: []
})
export class AuditModule {}
