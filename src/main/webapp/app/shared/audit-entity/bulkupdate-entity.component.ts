import { Component, AfterViewInit, Input } from '@angular/core';
import { AuditProcedure } from 'app/core/audit/auditprocedure.model';
import { DepartmentModel } from 'app/core/audit/department.model';
import { CostCentreModel } from 'app/core/audit/costCentre.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuditPlanService } from './../../audit/plan/auditplan.service';
import { AuditProcedureService } from './../../audit/procedure/auditprocedure.service';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';
import { JhiEventManager } from 'ng-jhipster';
@Component({
    selector: 'jhi-bulkupdate-entity-modal',
    templateUrl: './bulkupdate-entity.component.html'
})
export class JhiBulkUpdateEntityModalComponent implements AfterViewInit {
    @Input()
    auditEntityListForBulkUpdate: AuditProcedure[];
    selectedEntity: any;
    showDepartment: boolean;
    showCostCentre: boolean;
    invalidSelection: boolean;
    selectedDepartmentId: any;
    selectedCostCenterId: any;
    departList: DepartmentModel[];
    costCentreList: CostCentreModel[];
    alerts: any[];

    constructor(
        private activeModal: NgbActiveModal,
        private auditPlanService: AuditPlanService,
        private auditProcedureService: AuditProcedureService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    ngAfterViewInit() {
        this.showDepartment = false;
        this.showCostCentre = false;
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
                this.showCostCentre = false;
                break;
            case '2':
                this.showCostCentre = true;
                this.showDepartment = false;
                break;
            default:
                this.invalidSelection = true;
                break;
        }
    }

    bulkUpdate() {
        this.auditEntityListForBulkUpdate.forEach(element => {
            if (this.showCostCentre) {
                element.costCentreId = this.selectedCostCenterId;
            } else if (this.showDepartment) {
                element.deptartment = this.selectedDepartmentId;
            }
        });

        this.auditProcedureService
            .bulkUpdateProcedure(this.auditEntityListForBulkUpdate)
            .subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }

    private onSaveSuccess(result) {
        // this.addSuccessAlert('AuditPlan are updated successfully !!', 'audit.auditplan,.title');
        this.eventManager.broadcast({
            name: 'entityListModification',
            content: 'Entity Updated'
        });
        this.activeModal.dismiss('close');
        this.alertService.success('audit.procedure.bulkUpdate');
    }

    private onSaveError() {
        this.alertService.error('Error while updating audit plans !!');
    }
}
