export interface ITestingObservation {
    id?: any;
    observation?: any;
    monetaryImpact?: any;
    inference?: any;
    auditeeResponse?: any;
    deleteMe?: any;
}

export class TestingObservation implements ITestingObservation {
    constructor(
        public id?: any,
        public observation?: any,
        public monetaryImpact?: any,
        public inference?: any,
        public auditeeResponse?: any,
        public deleteMe?: any
    ) {
        this.id = id ? id : null;
        this.observation = observation ? observation : null;
        this.monetaryImpact = monetaryImpact ? monetaryImpact : null;
        this.inference = inference ? inference : null;
        this.auditeeResponse = auditeeResponse ? auditeeResponse : null;
        this.deleteMe = false;
    }
}
