import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DepartmentModel } from 'app/core/audit/department.model';
import { AuditPlanService } from '../plan/auditplan.service';
import { AuditProcedureService } from './auditprocedure.service';
import { AuditProcedure } from 'app/core/audit/auditprocedure.model';
import { AuditObjective } from 'app/core/audit/auditobjective.model';
import { CostCentreModel } from 'app/core/audit/costCentre.model';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { UserService } from 'app/core';

@Component({
    selector: 'jhi-audit-proc-update',
    templateUrl: './auditprocedure-update.component.html'
})
export class AuditProcedureUpdateComponent implements OnInit {
    departList: DepartmentModel[];
    costCentreList: CostCentreModel[];
    auditProcedure: AuditProcedure;
    auditProcedureObjectiveData: any[];
    auditProcedureObjectiveDataParams: any;
    isSaving: boolean;
    deptID: any;
    id: any;
    showObj: boolean = true;

    constructor(
        private route: ActivatedRoute,
        private auditplanService: AuditPlanService,
        private auditProcedureService: AuditProcedureService,
        private alertService: JhiAlertService,
        private userService: UserService,
        private router: Router
    ) {
        route.params.subscribe(params => (this.id = params.id));
    }

    showObjective(val) {
        this.showObj = val;
    }

    ngOnInit() {
        this.isSaving = false;
        this.auditProcedureObjectiveData = [];
        this.auditProcedureObjectiveDataParams = { objectiveName: '', objectiveDescription: '', active: true, id: null };
        if (this.id == 0) {
            this.auditProcedure = {};
            this.auditProcedure.active = true;
            this.auditProcedure.id = null;
        } else {
            this.auditProcedureService.queryProceduresById(this.id).subscribe(auditProcedure => {
                this.auditProcedure = auditProcedure;
                this.auditProcedureObjectiveData = auditProcedure.auditProcedureObjectiveData;
                if (this.auditProcedureObjectiveData && this.auditProcedureObjectiveData.length > 0) {
                    this.showObjective(false);
                }
            });
        }
        this.auditplanService.queryDepartments().subscribe(departList => {
            this.departList = departList;
        });

        this.auditplanService.queryCostCentre().subscribe(costCentreList => {
            this.costCentreList = costCentreList;
        });
    }

    addObjective() {
        this.showObjective(true);
        if (
            this.auditProcedureObjectiveDataParams.objectiveName &&
            this.auditProcedureObjectiveDataParams.objectiveDescription &&
            this.auditProcedureObjectiveDataParams.objectiveName != null &&
            this.auditProcedureObjectiveDataParams.objectiveDescription != null &&
            this.auditProcedureObjectiveDataParams.objectiveName != '' &&
            this.auditProcedureObjectiveDataParams.objectiveDescription != ''
        ) {
            this.auditProcedureObjectiveData.push(this.auditProcedureObjectiveDataParams);
            this.showObjective(false);
            this.auditProcedureObjectiveDataParams = { objectiveName: '', objectiveDescription: '', active: true, id: null };
        }
    }
    previousState() {
        this.router.navigate(['/auditprocedure']);
    }

    save() {
        this.auditProcedure.auditProcedureObjectiveData = this.auditProcedureObjectiveData;
        if (this.auditProcedureObjectiveData.length <= 0) {
            this.alertService.warning('audit.auditplan.objAdd');
        } else {
            this.auditProcedureService
                .createAuditProcedure(this.auditProcedure)
                .subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    private onSaveSuccess(result) {
        this.alertService.success('audit.auditplan.successMsg');
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    deleteObjective(index) {
        this.auditProcedureObjectiveData.splice(index, 1);
        if (this.auditProcedureObjectiveData.length == 0) {
            this.showObjective(true);
            this.auditProcedureObjectiveDataParams = { objectiveName: '', objectiveDescription: '', active: true, id: null };
        }
    }
}
