import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';

import { AuditTesting } from '../../core/audit/auditTesting.model';
import { AuditTestingService } from './audittesting.service';
import { DepartmentModel } from 'app/core/audit/department.model';
import { CostCentreModel } from 'app/core/audit/costCentre.model';
import { AuditPlanService } from '..';
import { AuditPlan, TestingObservation, User, UserService } from 'app/core';
import { HttpResponse } from '@angular/common/http';
import { AuditObjective } from 'app/core/audit/auditobjective.model';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';

@Component({
    selector: 'jhi-audit-testing-create',
    templateUrl: './audittesting-create.component.html'
})
export class AuditTestingCreateComponent implements OnInit {
    error: string;
    success: string;
    modalRef: NgbModalRef;
    auditPlan: AuditPlan;
    auditTesting: AuditTesting;
    auditeeId: any;
    department: DepartmentModel;
    costCentre: CostCentreModel;
    costCentreList: CostCentreModel[];
    riskAreaList: any;
    departmentList: DepartmentModel[];
    riskArea: any;
    testingResult: any;
    testingResultList: any[];
    testingObservations: TestingObservation[];
    auditplans: AuditPlan[];
    auditObjectives: AuditObjective[];
    userList: User[];
    id: any;
    actionTakenList: any;
    selectedFiles: FileList;
    hideAuditPlans: boolean = false;

    constructor(
        private auditTestingService: AuditTestingService,
        private route: ActivatedRoute,
        private router: Router,
        private auditplanService: AuditPlanService,
        private userService: UserService,
        private alertService: JhiAlertService
    ) {
        route.params.subscribe(params => (this.id = params.id));
    }
    change(event: any) {
        if (event.target.files && event.target.files[0]) {
            this.selectedFiles = event.target.files;
        }
    }

    ngOnInit() {
        this.hideAuditPlans = false;
        if (this.id == 0) {
            this.testingObservations = [];
            this.auditTesting = {
                id: '',
                riskAreaId: '',
                riskArea: '',
                expectedRevertDate: '',
                auditPlanId: '',
                auditPlanEntity: '',
                auditeeId: '',
                auditeeName: '',
                auditorId: '',
                auditorName: '',
                testingPlanObservationMasterId: '',
                testingPlanObservationData: [],
                deptartment: '',
                departmentName: '',
                costCentreId: '',
                costCentreName: '',
                testResult: '',
                testResultId: '',
                rating: '',
                auditeeResponse: '',
                actionTakenId: '',
                actionTaken: '',
                testDescription: '',
                files: [],
                locked: false,
                path: ''
            };
        } else {
            this.auditTestingService.queryTestingPlanById(this.id).subscribe(auditTesting => {
                this.onSuccessResponse(auditTesting);
            });
        }
        this.auditTestingService.queryRiskArea().subscribe(data => {
            this.riskAreaList = data;
        });
        this.auditTestingService.queryTestingResult().subscribe(data => {
            this.testingResultList = data;
        });

        this.auditplanService.queryDepartments().subscribe(departmentList => {
            this.departmentList = departmentList;
        });
        this.auditplanService.queryCostCentre().subscribe(costCentreList => {
            this.costCentreList = costCentreList;
        });

        this.auditTestingService.queryActionTaken().subscribe(actionTaken => {
            this.actionTakenList = actionTaken;
        });
        this.userService.queryusers().subscribe(userList => {
            //this.userList = userList;

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
        this.loadAuditPlanList();
    }

    onChange(common) {
        this.testingObservations = [{}];
        this.auditObjectives = this.auditplans.filter(s => s.id == common)[0].auditPlanObjectiveData;
        this.auditObjectives.forEach(element => {
            this.testingObservations.push({});
        });
    }

    trackIdentity(index, item: User) {
        return index;
    }
    save() {
        this.auditTesting.testingPlanObservationData = this.testingObservations;
        const formdata: FormData = new FormData();
        if (this.selectedFiles !== undefined && this.selectedFiles.length > 0) {
            for (let i = 0; i < this.selectedFiles.length; i++) {
                formdata.append('files', this.selectedFiles[i], this.selectedFiles[i].name);
            }
        }
        formdata.append('auditTesting', JSON.stringify(this.auditTesting));
        this.auditTestingService.createAuditTesting(formdata).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
    }

    deleteLastAddedObservation(index?: any) {
        this.testingObservations.splice(index, 1);
    }
    onSuccessResponse(auditTesting) {
        this.auditTesting = auditTesting;

        this.hideAuditPlans = this.auditTesting.testDescription != '' ? false : true;

        if (this.hideAuditPlans) {
            this.auditTesting.auditPlanId = auditTesting.auditPlanId;
        }
        this.testingObservations = this.auditTesting.testingPlanObservationData;
    }
    onSaveSuccess(result) {
        this.router.navigateByUrl('/audittesting');
        this.alertService.success('audit.testing.successMsg');
    }

    onSaveError() {}

    loadAuditPlanList() {
        this.auditplanService
            .queryAuditorPlans()
            .subscribe(
                (res: HttpResponse<AuditPlan[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    private onSuccess(data, headers) {
        this.auditplans = data;
    }

    private onError(error) {
        // this.alertService.error(error.error, error.message, null);
    }

    previousState() {
        this.router.navigateByUrl('/audittesting');
    }

    previousPageState() {
        window.history.back();
    }

    addObjective() {
        this.testingObservations.push({ deleteMe: true });
    }
    downloadFileService(file: string, id: any) {
        console.log(file + id);
        this.auditTestingService.downFile(file, id).subscribe(data => this.downloadFile(file, data)),
            error => console.log('Error while downloading ' + file),
            () => console.log('OK');
    }

    downloadFile(fileType: string, data: any) {
        const blob = new Blob([data], { type: 'text/' + fileType.split('.')[0] });
        const url = window.URL.createObjectURL(blob);
        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, fileType);
        } else {
            let a = document.createElement('a');
            a.href = url;
            a.download = fileType;
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    }

    showTestDescription() {
        this.hideAuditPlans = !this.hideAuditPlans;
    }

    lockAuditTesting() {
        if (confirm('Are you sure to lock Audit Testing')) {
            this.auditTesting.locked = true;
            this.auditTestingService.lockAuditTesting(this.auditTesting).subscribe(response => this.successFullyLocked(response));
        }
    }

    successFullyLocked(response: any) {
        this.router.navigateByUrl('audittesting');
        this.alertService.success('audit.testing.successFullyLocked');
    }

    unlockAuditTesting() {
        if (confirm('Are you sure to unlock Audit Testing')) {
            this.auditTesting.locked = false;
            this.auditTestingService.lockAuditTesting(this.auditTesting).subscribe(response => this.successFullyLocked(response));
        }
    }
}
