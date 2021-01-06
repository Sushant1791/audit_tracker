export interface ICostCentre {
    id?: any;
    costCentreName?: String;
}

export class CostCentreModel implements ICostCentre {
    constructor(public id?: any, public costCentreName?: String) {
        this.id = id ? id : null;
        this.costCentreName = costCentreName ? costCentreName : 'NA';
    }
}
