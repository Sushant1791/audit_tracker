import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AuditPlan } from 'app/core/audit/auditplan.model';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.model';
import { DepartmentModel } from 'app/core/audit/department.model';
import { AuditPlanService } from 'app/audit/plan/auditplan.service';
import { AuditProcedure } from 'app/core/audit/auditprocedure.model';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';
import { CostCentreModel } from 'app/core/audit/costCentre.model';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'jhi-audit-mgmt-update',
    templateUrl: './auditplan-update.component.html'
})
export class AuditPlanUpdateComponent implements OnInit {
    auditplan: any;
    auditplanDetails: any;
    costCentreList: CostCentreModel[];
    alerts: any[];
    selectedDepartmentId: any;
    selectedCostCenterId: any;
    languages: any[];
    authorities: any[];
    selected_assignTo: any;
    selected_procedureId: any;
    isSaving: boolean;
    userList: User[];
    departList: DepartmentModel[];
    auditProcedureList: AuditProcedure[];
    auditPlanEntity: String;
    startDate: any;
    endDate: any;
    remarks: any;
    attachAuditPlan: boolean = false;
    objectiveData: any;
    procedureObjective: AuditProcedure;
    id: any;
    locationList: any;
    locationId: any;

    showObj: boolean = true;
    auditProcedureObjectiveData: any[];
    auditProcedureObjectiveDataParams: any;

    constructor(
        private router: Router,
        private auditplanService: AuditPlanService,
        private route: ActivatedRoute,
        private userService: UserService,
        private alertService: JhiAlertService,
        private datePipe: DatePipe
    ) {
        route.params.subscribe(params => (this.id = params.id));
    }

    ngOnInit() {
        this.isSaving = false;
        this.auditProcedureObjectiveData = [];
        this.auditProcedureObjectiveDataParams = { objectiveName: '', objectiveDescription: '', active: true, id: null };
        console.log('this.id : ', this.id);
        if (this.id == 0) {
            this.auditplan = {};
            this.auditplan.assignTo = {};
            this.auditplan.procedure = {};
            /*this.objectiveData = [
                {
                    objectiveName: '',
                    objectiveDescription: '',
                    active: true
                }
            ];
            this.route.data.subscribe(({ auditplan }) => {
                this.auditplan = auditplan.body ? auditplan.body : auditplan;
            });*/
        } else {
            this.auditplanService.queryPlanById(this.id).subscribe(auditPlan => {
                this.auditplan = auditPlan;
                this.auditplanDetails = auditPlan;

                this.auditProcedureObjectiveData = auditPlan.auditPlanObjectiveData;
                this.showObjective(false);

                this.selected_assignTo = this.auditplan.assignTo.id;
                this.selected_procedureId = this.auditplan.procedure.id;
                this.auditPlanEntity = this.auditplan.auditPlanEntity;

                console.log('selected_procedureId : ', this.selected_procedureId);
                if (this.selected_procedureId && this.selected_procedureId != null) {
                    this.attachAuditPlan = true;
                } else {
                    this.attachAuditPlan = false;
                }

                this.startDate = this.auditplan.startDate;
                this.endDate = this.auditplan.endDate;
                this.selectedCostCenterId = this.auditplan.costCentreId;
                this.selectedDepartmentId = this.auditplan.department;
                this.remarks = this.auditplan.remarks;
                this.objectiveData = this.auditplan.auditPlanObjectiveData;
                this.locationId = this.auditplan.locationId;
            });
        }

        this.alerts = this.alertService.get();
        this.userService.queryusers().subscribe(userList => {
            this.userList = [];
            let temp = userList;
            if (temp) {
                for (var i = 0; i < temp.length; i++) {
                    if (
                        parseInt(temp[i].id) === 1 ||
                        parseInt(temp[i].id) === 2 ||
                        parseInt(temp[i].id) === 3 ||
                        parseInt(temp[i].id) === 4
                    ) {
                    } else {
                        this.userList.push(temp[i]);
                    }
                }
            }
        });

        this.auditplanService.queryDepartments().subscribe(departList => {
            this.departList = departList;
        });

        this.auditplanService.queryProcedureList().subscribe(auditProcedureList => {
            this.auditProcedureList = auditProcedureList;
        });
        this.auditplanService.queryCostCentre().subscribe(costCentreList => {
            this.costCentreList = costCentreList;
        });

        this.userService.querylocations().subscribe(response => {
            this.locationList = response;
        });
        this.procedureObjective = {};
    }

    /* setClasses(alert) {
        return {
            toast: !!alert.toast,
            [alert.position]: true
        };
    }*/

    previousState() {
        this.router.navigate(['/auditplan']);
    }

    onChange(common) {
        this.procedureObjective = this.auditProcedureList.filter(s => s.id == common)[0];
        this.selectedDepartmentId = this.procedureObjective.deptartment;
        this.selectedCostCenterId = this.procedureObjective.costCentreId;
    }

    save() {
        this.auditplan.assignTo.id = this.selected_assignTo;
        this.auditplan.procedure.id = this.selected_procedureId;
        this.auditplan.auditPlanEntity = this.auditPlanEntity;
        this.auditplan.startDate = this.startDate;
        this.auditplan.endDate = this.endDate;
        this.auditplan.costCentreId = this.selectedCostCenterId;
        this.auditplan.department = this.selectedDepartmentId;
        this.auditplan.remarks = this.remarks;
        this.auditplan.auditPlanObjectiveData = this.objectiveData;
        this.auditplan.locationId = this.locationId;
        this.auditplan.auditPlanObjectiveData = this.auditProcedureObjectiveData;
        console.log('attachAuditPlan check : ', this.attachAuditPlan);
        if (this.auditProcedureObjectiveData.length <= 0) {
            this.alertService.warning('audit.auditplan.objAdd');
        } else {
            this.auditplanService
                .createAuditPlan(this.auditplan)
                .subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.addSuccessAlert('AuditPlan created successfully !!');
        this.router.navigateByUrl('/auditplan');
    }

    private onSaveError() {
        this.isSaving = false;
    }

    addSuccessAlert(message: any, key?, data?) {
        this.alertService.success('audit.auditplan.successMsg');
    }

    /*addObjective() {
        this.objectiveData.push({
            objectiveName: '',
            objectiveDescription: '',
            active: true
        });
    }*/

    showObjective(val) {
        this.showObj = val;
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

    deleteObjective(index) {
        this.auditProcedureObjectiveData.splice(index, 1);
        if (this.auditProcedureObjectiveData.length == 0) {
            this.showObjective(true);
            this.auditProcedureObjectiveDataParams = { objectiveName: '', objectiveDescription: '', active: true, id: null };
        }
    }

    /*deleteObjective(index) {
        this.objectiveData.splice(index, 1);
    }*/
}
