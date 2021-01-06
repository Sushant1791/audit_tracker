import { TestingObservation } from './testing-observation.model';
import { User } from '../user/user.model';

export interface IAuditTesting {
    id?: any;
    riskAreaId?: any;
    riskArea?: any;
    expectedRevertDate?: any;
    auditPlanId?: any;
    auditPlanEntity?: string;
    auditeeId?: any;
    auditeeName?: any;
    auditorId?: User;
    deptartment?: any;
    departmentName?: any;
    auditorName?: any;
    active?: boolean;
    testingPlanObservationMasterId?: any;
    testingPlanObservationData?: TestingObservation[];
    costCentreId?: any;
    costCentreName?: any;
    testResult?: any;
    isChecked?: boolean;
    rating?: any;
    responded?: boolean;
    auditeeResponse?: any;
    overDueDays?: any;
    showObjectives?: boolean;
    actionTakenId?: any;
    actionTaken?: any;
    testResultId?: any;
    ratingId?: any;
    files?: any;
    testDescription?: any;
    auditPlanLinker?: boolean;
    locked?: boolean;
    path?: any;
}

export class AuditTesting implements IAuditTesting {
    constructor(
        public id?: any,
        public riskAreaId?: any,
        public riskArea?: any,
        public expectedRevertDate?: any,
        public auditPlanId?: any,
        public auditPlanEntity?: string,
        public auditeeId?: any,
        public auditeeName?: any,
        public auditorId?: any,
        public auditorName?: any,
        public active?: boolean,
        public testingPlanObservationMasterId?: any,
        public testingPlanObservationData?: TestingObservation[],
        public deptartment?: any,
        public departmentName?: any,
        public costCentreId?: any,
        public costCentreName?: any,
        public testResult?: any,
        public testResultId?: any,
        public rating?: any,
        public ratingId?: any,
        public isChecked?: boolean,
        public responded?: boolean,
        public auditeeResponse?: any,
        public overDueDays?: number,
        public showObjectives?: boolean,
        public actionTakenId?: any,
        public actionTaken?: any,
        public files?: any,
        public testDescription?: any,
        public auditPlanLinked?: boolean,
        public locked?: boolean,
        public path?: any
    ) {
        this.id = id ? id : null;
        this.ratingId = ratingId ? ratingId : null;
        this.testResultId = testResultId ? testResultId : null;
        this.riskAreaId = riskAreaId ? riskAreaId : null;
        this.riskArea = riskArea ? riskArea : null;
        this.expectedRevertDate = expectedRevertDate ? expectedRevertDate : null;
        this.auditPlanId = auditPlanId ? auditPlanId : null;
        this.auditPlanEntity = auditPlanEntity ? auditPlanEntity : null;
        this.auditeeId = auditeeId ? auditeeId : null;
        this.auditeeName = auditeeName ? auditeeName : null;
        this.auditorId = auditorId ? auditorId : null;
        this.active = active ? true : false;
        this.auditorName = auditorName ? auditorName : null;
        this.departmentName = departmentName ? departmentName : null;
        this.deptartment = deptartment ? deptartment : null;
        this.costCentreId = costCentreId ? costCentreId : null;
        this.costCentreName = costCentreName ? costCentreName : null;
        this.testResult = testResult ? testResult : null;
        this.testingPlanObservationData = testingPlanObservationData ? testingPlanObservationData : null;
        this.testingPlanObservationMasterId = testingPlanObservationMasterId ? testingPlanObservationMasterId : null;
        this.isChecked = isChecked ? true : false;
        this.rating = rating ? rating : null;
        this.auditeeResponse = auditeeResponse ? auditeeResponse : null;
        this.responded = responded ? true : false;
        this.showObjectives = false;
        this.overDueDays = 0;
        this.actionTakenId = actionTakenId ? actionTakenId : 0;
        this.actionTaken = actionTaken ? actionTaken : '';
        this.files = files ? files : [];
        this.testDescription = testDescription ? testDescription : null;
        this.auditPlanLinked = auditPlanLinked ? true : false;
        this.locked = locked ? true : false;
        this.path = path ? path : '';
    }
}
