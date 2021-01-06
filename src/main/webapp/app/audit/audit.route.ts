import { Routes } from '@angular/router';

import { auditPlanRoute } from './';
import { auditProcedureRoute } from './';
import { auditTestingRoute } from './testing/audittesting.route';
import { auditeeRoute } from './auditee/auditee.route';
import { auditReportRoute } from './report/audit-report.route';

const AUDITTRACKER_ROUTES = [...auditPlanRoute, ...auditProcedureRoute, ...auditTestingRoute, ...auditeeRoute, ...auditReportRoute];

export const auditState: Routes = [
    {
        path: '',
        children: AUDITTRACKER_ROUTES
    }
];
