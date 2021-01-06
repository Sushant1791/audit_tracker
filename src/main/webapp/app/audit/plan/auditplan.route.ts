import { Routes } from '@angular/router';

import { AuditPlanComponent } from './auditplan.component';
import { UserRouteAccessService } from 'app/core';
import { AuditPlanUpdateComponent } from './auditplan-update.component';

export const auditPlanRoute: Routes = [
    {
        path: 'auditplan',
        component: AuditPlanComponent,
        data: {
            authorities: [],
            pageTitle: 'audit.auditplan.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auditplan/new/:id',
        component: AuditPlanUpdateComponent,
        data: {
            pageTitle: 'audit.auditplan.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
