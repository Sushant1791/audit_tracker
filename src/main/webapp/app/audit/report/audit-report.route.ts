import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { AuditReportComponent } from './audit-report.component';
import { AuditReportGenerateComponent } from './audit-report-generate.component';

export const auditReportRoute: Routes = [
    {
        path: 'audit-report',
        component: AuditReportComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audit-report-generate/:department/:month/:year/:location',
        component: AuditReportGenerateComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
