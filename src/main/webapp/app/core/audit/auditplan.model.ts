import { User } from '../user/user.model';
import { AuditProcedure } from './auditprocedure.model';
import { AuditObjective } from './auditobjective.model';

export interface IAuditPlan {
    id?: any;
    remarks?: string;
    startDate?: string;
    endDate?: string;
    isActive?: string;
    assignTo?: User;
    createdBy?: string;
    createdDate?: Date;
    lastModifiedBy?: string;
    lastModifiedDate?: Date;
    auditPlanEntity?: String;
    procedure?: AuditProcedure;
    auditPlanObjectiveData?: AuditObjective[];
    department?: any;
    costCentreId?: any;
    isChecked?: boolean;
    locationId?: any;
}

export class AuditPlan implements IAuditPlan {
    constructor(
        public id?: any,
        public remarks?: string,
        public startDate?: string,
        public endDate?: string,
        public isActive?: string,
        public assignTo?: User,
        public createdBy?: string,
        public createdDate?: Date,
        public lastModifiedBy?: string,
        public lastModifiedDate?: Date,
        public auditPlanEntity?: String,
        public procedure?: AuditProcedure,
        public auditPlanObjectiveData?: AuditObjective[],
        public department?: any,
        public costCentreId?: any,
        public locationId?: any,
        public isChecked?: boolean
    ) {
        this.id = id ? id : null;
        this.remarks = remarks ? remarks : 'NA';
        this.startDate = startDate ? startDate : null;
        this.endDate = endDate ? endDate : null;
        this.isActive = isActive ? isActive : null;
        this.createdBy = createdBy ? createdBy : null;
        this.createdDate = createdDate ? createdDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.assignTo = assignTo ? assignTo : {};
        this.auditPlanEntity = auditPlanEntity ? auditPlanEntity : null;
        this.procedure = procedure ? procedure : null;
        this.auditPlanObjectiveData = auditPlanObjectiveData ? auditPlanObjectiveData : null;
        this.department = department ? department : null;
        this.costCentreId = costCentreId ? costCentreId : null;
        this.isChecked = isChecked ? true : false;
        this.locationId = locationId ? locationId : null;
    }
}
