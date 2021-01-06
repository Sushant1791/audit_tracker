import { Routes } from '@angular/router';

import { AuditTestingComponent } from './audittesting.component';
import { UserRouteAccessService } from 'app/core';
import { AuditTestingCreateComponent } from './audittesting-create.component';

export const auditTestingRoute: Routes = [
    {
        path: 'audittesting',
        component: AuditTestingComponent,
        data: {
            authorities: [],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'audittesting/new/:id',
        component: AuditTestingCreateComponent,
        data: {
            authorities: [],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
