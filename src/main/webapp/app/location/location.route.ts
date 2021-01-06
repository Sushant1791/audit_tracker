import { Routes } from '@angular/router';
import { LocationComponent, LocationUpdateComponent } from './';
import { UserRouteAccessService } from 'app/core';

export const LOCATION_ROUTE: Routes = [
    {
        path: 'locations',
        component: LocationComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'home.title'
        }
    },
    {
        path: 'locations/new/:id',
        component: LocationUpdateComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'audit.testing.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
