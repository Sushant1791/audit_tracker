import { Component, AfterViewInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuditPlan } from 'app/core/audit/auditplan.model';
import { AuditPlanService } from 'app/audit';
import { JhiAlertService } from 'ng-jhipster';
import { Router } from '@angular/router';
import { User } from 'app/core';
import { DepartmentModel } from 'app/core/audit/department.model';
import { CostCentreModel } from 'app/core/audit/costCentre.model';

@Component({
    selector: 'jhi-bulkupdate-plan-modal',
    templateUrl: './bulkupdate-plan.component.html'
})
export class JhiBulkUpdatePlanModalComponent implements AfterViewInit {
    @Input()
    auditPlanListForBulkUpdate: AuditPlan[];
    selectedEntity: any;
    @Input()
    userList: User[];
    departList: DepartmentModel[];
    costCentreList: CostCentreModel[];
    selected_assignTo: any;
    selectedDepartmentId: any;
    selectedCostCenterId: any;
    startDate: any;
    showDepartment: boolean;
    showCostCentre: boolean;
    showSDateAndEDate: boolean;
    showAssignTo: boolean;
    alerts: any[];
    endDate: any;

    constructor(
        private activeModal: NgbActiveModal,
        private router: Router,
        private auditPlanService: AuditPlanService,
        private alertService: JhiAlertService
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    ngAfterViewInit() {
        this.alerts = this.alertService.get();
        this.showAssignTo = false;
        this.showDepartment = false;
        this.showCostCentre = false;
        this.showSDateAndEDate = false;
        this.auditPlanService.queryDepartments().subscribe(departList => {
            this.departList = departList;
        });
        this.auditPlanService.queryCostCentre().subscribe(costCentreList => {
            this.costCentreList = costCentreList;
        });
    }
    onChange(field) {
        switch (field) {
            case '1':
                this.showDepartment = true;
                this.showAssignTo = false;
                this.showSDateAndEDate = false;
                this.showCostCentre = false;
                break;
            case '2':
                this.showCostCentre = true;
                this.showDepartment = false;
                this.showAssignTo = false;
                this.showSDateAndEDate = false;
                break;
            case '3':
                this.showSDateAndEDate = true;
                this.showDepartment = false;
                this.showAssignTo = false;
                this.showCostCentre = false;
                break;
            case '4':
                this.showAssignTo = true;
                this.showDepartment = false;
                this.showSDateAndEDate = false;
                this.showCostCentre = false;
                break;
            default:
                console.log('Invalid Selection');
                break;
        }
    }

    bulkUpdate() {
        this.auditPlanListForBulkUpdate.forEach(element => {
            if (this.showAssignTo) {
                element.assignTo = { id: this.selected_assignTo };
            } else if (this.showCostCentre) {
                element.costCentreId = this.selectedCostCenterId;
            } else if (this.showDepartment) {
                element.department = this.selectedDepartmentId;
            } else if (this.showSDateAndEDate) {
                element.startDate = this.startDate;
                element.endDate = this.endDate;
            }
        });

        this.auditPlanService
            .bulkUpdateAuditPlans(this.auditPlanListForBulkUpdate)
            .subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }

    private onSaveSuccess(result) {
        // this.addSuccessAlert('AuditPlan are updated successfully !!', 'audit.auditplan,.title');
        this.activeModal.dismiss('close');
        this.router.navigateByUrl('/auditplan');
        this.addSuccessAlert('audit.auditplan.bulkUpdate');
    }

    private onSaveError() {
        this.addErrorAlert('Error while updating audit plans !!');
    }

    addErrorAlert(message: any, key?, data?) {
        key = key && key !== null ? key : message;
        this.alerts.push(
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: message,
                    params: data,
                    timeout: 5000,
                    toast: this.alertService.isToast(),
                    scoped: true
                },
                this.alerts
            )
        );
    }

    addSuccessAlert(message: any, key?, data?) {
        key = key && key !== null ? key : message;
        this.alerts.push(
            this.alertService.addAlert(
                {
                    type: 'success',
                    msg: message,
                    params: data,
                    timeout: 5000,
                    toast: this.alertService.isToast(),
                    scoped: true
                },
                this.alerts
            )
        );
    }
}
