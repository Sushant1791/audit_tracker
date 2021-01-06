import { Routes } from '@angular/router';

import { AuditProcedureComponent } from './auditprocedure.component';
import { UserRouteAccessService } from 'app/core';
import { AuditProcedureUpdateComponent } from './auditprocedure-update.component';
import { AuditProcedureObjectiveComponent } from './auditprocedure-objective.component';
export const auditProcedureRoute: Routes = [
    {
        path: 'auditprocedure',
        component: AuditProcedureComponent,
        data: {
            authorities: [],
            pageTitle: 'audit.procedure.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auditprocedure/new/:id',
        component: AuditProcedureUpdateComponent
    },
    {
        path: 'auditprocedure/showProcedure/:id',
        component: AuditProcedureObjectiveComponent
    }
];
