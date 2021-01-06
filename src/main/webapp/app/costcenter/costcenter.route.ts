import { Routes } from '@angular/router';
import { CostCentreComponent, CostCentreUpdateComponent } from './';
import { UserRouteAccessService } from 'app/core';

export const COSTCENTRE_ROUTE: Routes = [
    {
        path: 'costcentre',
        component: CostCentreComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'home.title'
        }
    },
    {
        path: 'costcentre/new/:id',
        component: CostCentreUpdateComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
