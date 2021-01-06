export interface IAuditObjective {
    id?: any;
    objectiveName?: String;
    objectiveDescription?: String;
    active?: boolean;
    isEdit?: boolean;
}
export class AuditObjective implements IAuditObjective {
    constructor(
        public id?: any,
        public objectiveName?: String,
        public objectiveDescription?: String,
        public active?: boolean,
        public isEdit?: boolean
    ) {
        this.id = id ? id : null;
        this.objectiveName = objectiveName ? objectiveName : null;
        this.objectiveDescription = objectiveDescription ? objectiveDescription : null;
        this.active = active ? true : false;
        this.isEdit = false;
    }
}
