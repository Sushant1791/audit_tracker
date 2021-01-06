import { AuditObjective } from './auditobjective.model';

export interface IAuditProcedure {
    id?: any;
    procedureTitle?: String;
    procedureDescription?: String;
    active?: boolean;
    deptartment?: any;
    departmentName?: String;
    costCentreId?: any;
    costCentreName?: String;
    auditProcedureObjectiveData?: AuditObjective[];
    isChecked?: boolean;
}
export class AuditProcedure implements IAuditProcedure {
    constructor(
        public id?: any,
        public procedureTitle?: String,
        public procedureDescription?: String,
        public active?: boolean,
        public deptartment?: any,
        public departmentName?: String,
        public costCentreId?: any,
        public costCentreName?: String,
        public auditProcedureObjectiveData?: AuditObjective[],
        public isChecked?: boolean
    ) {
        this.id = id ? id : null;
        this.procedureTitle = procedureTitle ? procedureTitle : null;
        this.procedureDescription = procedureDescription ? procedureDescription : null;
        this.deptartment = deptartment ? deptartment : null;
        this.auditProcedureObjectiveData = auditProcedureObjectiveData ? auditProcedureObjectiveData : [];
        this.active = active ? true : false;
        this.departmentName = departmentName ? departmentName : null;
        this.costCentreId = costCentreId ? costCentreId : null;
        this.costCentreName = costCentreName ? costCentreName : null;
        this.isChecked = isChecked ? true : false;
    }
}
