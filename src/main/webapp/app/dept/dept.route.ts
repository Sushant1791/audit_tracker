import { Routes } from '@angular/router';
import { DeptComponent, DeptUpdateComponent } from './';
import { UserRouteAccessService } from 'app/core';

export const DEPT_ROUTE: Routes = [
    {
        path: 'department',
        component: DeptComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'home.title'
        }
    },
    {
        path: 'department/new/:id',
        component: DeptUpdateComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
