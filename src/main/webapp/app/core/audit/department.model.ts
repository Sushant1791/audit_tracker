export interface IDepartmentModel {
    id?: any;
    deptName?: String;
}

export class DepartmentModel implements IDepartmentModel {
    constructor(public id?: any, public deptName?: String) {
        this.id = id ? id : null;
        this.deptName = deptName ? deptName : 'NA';
    }
}
