import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { AuditeeUpdateComponent } from './auditee-update.component';
import { AuditeeWindowComponent } from './auditee-window.component';

export const auditeeRoute: Routes = [
    {
        path: 'auditee-window',
        component: AuditeeWindowComponent,
        data: {
            authorities: [],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auditee-update/:id',
        component: AuditeeUpdateComponent,
        data: {
            authorities: [],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
