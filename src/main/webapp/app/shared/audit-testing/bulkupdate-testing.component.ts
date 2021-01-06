import { AfterViewInit, Input, Component } from '@angular/core';

import { AuditPlanService } from 'app/audit/plan/auditplan.service';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';
import { User } from 'app/core/user/user.model';
import { DepartmentModel } from 'app/core/audit/department.model';
import { CostCentreModel } from 'app/core/audit/costCentre.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'app/core/user/user.service';
import { AuditTesting } from 'app/core/audit/auditTesting.model';
import { AuditTestingService } from 'app/audit/testing/audittesting.service';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-bulkupdate-testing-modal',
    templateUrl: './bulkupdate-testing.component.html'
})
export class JhiBulkUpdateTestingModalComponent implements AfterViewInit {
    @Input()
    auditTestingListForBulkUpdate: AuditTesting[];

    selectedEntity: any;
    userList: User[];
    departmentList: DepartmentModel[];
    costCentreList: CostCentreModel[];
    selected_auditee: any;
    selectedDepartmentId: any;
    riskAreaList: any;
    selectedCostCenterId: any;
    expectedRevertDate: any;
    riskAreaValue: any;
    testingResultId: any;
    rating: any;
    showDepartment: boolean;
    showCostCentre: boolean;
    showRiskArea: boolean;
    showAuditee: boolean;
    showTestingResult: boolean;
    showRating: boolean;
    showExpectedDate: boolean;
    alerts: any[];
    endDate: any;
    testingResultList: any;

    constructor(
        private auditplanService: AuditPlanService,
        private userService: UserService,
        private alertService: JhiAlertService,
        private activeModal: NgbActiveModal,
        private auditTestingService: AuditTestingService,
        private eventManager: JhiEventManager
    ) {}
    clear() {
        this.activeModal.dismiss('cancel');
    }
    ngAfterViewInit() {
        this.alerts = this.alertService.get();
        this.showAuditee = false;
        this.showDepartment = false;
        this.showTestingResult = false;
        this.showCostCentre = false;
        this.showRiskArea = false;
        this.showRating = false;
        this.showExpectedDate = false;
        this.riskAreaList = [{ value: 1, area: 'Process' }, { value: 2, area: 'Procedure' }];
        this.testingResultList = [
            { id: 1, value: 'Planned' },
            { id: 2, value: 'In Process' },
            { id: 3, value: 'Non Compliance' },
            { id: 4, value: 'Complied' },
            { id: 5, value: 'For Information Only' },
            { id: 6, value: 'Others (Type)' }
        ];

        this.auditplanService.queryDepartments().subscribe(departmentList => {
            this.departmentList = departmentList;
        });
        this.auditplanService.queryCostCentre().subscribe(costCentreList => {
            this.costCentreList = costCentreList;
        });

        this.userService.queryusers().subscribe(userList => {
            this.userList = userList;
        });
    }
    onChange(field) {
        switch (field) {
            case '1':
                this.showAuditee = false;
                this.showDepartment = true;
                this.showTestingResult = false;
                this.showCostCentre = false;
                this.showRiskArea = false;
                this.showRating = false;
                this.showExpectedDate = false;
                break;
            case '2':
                this.showAuditee = false;
                this.showDepartment = false;
                this.showTestingResult = false;
                this.showCostCentre = true;
                this.showRiskArea = false;
                this.showRating = false;
                this.showExpectedDate = false;
                break;
            case '3':
                this.showAuditee = false;
                this.showDepartment = false;
                this.showTestingResult = false;
                this.showCostCentre = false;
                this.showRiskArea = true;
                this.showRating = false;
                this.showExpectedDate = false;
                break;
            case '4':
                this.showAuditee = false;
                this.showDepartment = false;
                this.showTestingResult = true;
                this.showCostCentre = false;
                this.showRiskArea = false;
                this.showRating = false;
                this.showExpectedDate = false;
                break;
            case '5':
                this.showAuditee = false;
                this.showDepartment = false;
                this.showTestingResult = false;
                this.showCostCentre = false;
                this.showRiskArea = false;
                this.showRating = true;
                this.showExpectedDate = false;
                break;
            case '6':
                this.showAuditee = false;
                this.showDepartment = false;
                this.showTestingResult = false;
                this.showCostCentre = false;
                this.showRiskArea = false;
                this.showRating = false;
                this.showExpectedDate = true;
                break;
            case '7':
                this.showAuditee = true;
                this.showDepartment = false;
                this.showTestingResult = false;
                this.showCostCentre = false;
                this.showRiskArea = false;
                this.showRating = false;
                this.showExpectedDate = false;
                break;
            default:
                this.alertService.warning('Invalid Selection');
                break;
        }
    }

    bulkUpdate() {
        this.auditTestingListForBulkUpdate.forEach(element => {
            if (this.showAuditee) {
                element.auditeeId = this.selected_auditee;
            } else if (this.showCostCentre) {
                element.costCentreId = this.selectedCostCenterId;
            } else if (this.showDepartment) {
                element.deptartment = this.selectedDepartmentId;
            } else if (this.showExpectedDate) {
                element.expectedRevertDate = this.expectedRevertDate;
            } else if (this.showRating) {
                element.rating = this.rating;
            } else if (this.showRiskArea) {
                element.riskAreaId = this.riskAreaValue;
            } else if (this.showTestingResult) {
                element.testResult = this.testingResultId;
            }
        });
        this.auditTestingService
            .bulkUpdateTests(this.auditTestingListForBulkUpdate)
            .subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }

    onSaveError() {
        this.alertService.error('Error while updating audit plans !!');
        this.activeModal.dismiss('close');
    }

    onSaveSuccess(response) {
        this.eventManager.broadcast({
            name: 'auditTestListModification',
            content: 'Testing Updated'
        });
        this.activeModal.dismiss('close');
        this.alertService.success('audit.testing.successUpdateMsg');
    }
}
