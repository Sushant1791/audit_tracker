import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';

import { AuditTesting } from 'app/core/audit/auditTesting.model';
import { AuditTestingService } from './audittesting.service';
import { AuditPlanService } from '../plan/auditplan.service';
import { DatePipe } from '@angular/common';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { BulkUpdatePlanModalService } from 'app/core/audit/bulkupdate-plan.service';
import { Principal, UserService } from 'app/core';

@Component({
    selector: 'jhi-audit-testing',
    templateUrl: './audittesting.component.html'
})
export class AuditTestingComponent implements OnInit {
    error: string;
    success: string;
    modalRef: NgbModalRef;
    auditTestingList: AuditTesting[];
    auditTestingListForBulkUpdate: AuditTesting[];
    page: any;
    searchaudit: any;
    totalCount: any;
    currentAccount: any;
    searchTestingPlan: any;
    userList: any;

    constructor(
        private auditPlanService: AuditPlanService,
        private datePipe: DatePipe,
        private auditTestingService: AuditTestingService,
        private route: ActivatedRoute,
        private router: Router,
        private alertService: JhiAlertService,
        private principal: Principal,
        private bulkUpdateService: BulkUpdatePlanModalService,
        private eventManager: JhiEventManager,
        private userService: UserService
    ) {}

    ngOnInit() {
        this.searchTestingPlan = { auditPlanEntity: '', start_date: '', end_date: '', status: '', respond: '', auditee: 0, auditor: 0 };
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.totalCount = 0;
            this.page = 0;

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

            this.getAllTestingPlans(this.page, this.searchTestingPlan);
            this.eventManager.subscribe('auditTestListModification', response =>
                this.getAllTestingPlans(this.page, this.searchTestingPlan)
            );
        });

        this.auditTestingListForBulkUpdate = [];
    }

    getAllTestingPlans(page, searchTestingPlan) {
        this.page = page;
        this.auditTestingService.getAllTestingForTestingScreen(page, searchTestingPlan).subscribe(response => {
            this.auditTestingList = response['list'];
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
        });
    }

    /*loadAll(page, job) {
        this.page = page;
        this.auditTestingService.queryAuditTesting(this.page, page).subscribe(auditTestingList => {
            this.auditTestingList = auditTestingList;
            console.log(JSON.stringify(this.auditTestingList));
        });
    }

    loadMore() {
        this.page = this.page + 1;
        this.auditTestingService.queryAuditTesting(this.page, this.searchaudit).subscribe(auditTestingList => {
            this.auditTestingList = this.auditTestingList.concat(auditTestingList);
        });
    }*/

    downLoadCSV() {
        let module = 'Testing';
        this.auditPlanService.downLoadCSV(module).subscribe(data => this.downloadFile(data, module)),
            error => console.log('Error downloading the file.'),
            () => console.log('OK');
    }

    downloadFile(data: any, module: string) {
        const blob = new Blob([data], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);
        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, module + '_' + this.datePipe.transform(new Date(), 'yyyy-MM-dd') + '.csv');
        } else {
            let a = document.createElement('a');
            a.href = url;
            a.download = module + '_' + this.datePipe.transform(new Date(), 'yyyy-MM-dd') + '.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    }

    previousPageState() {
        window.history.back();
    }
    bulkUpdateModal() {
        if (this.auditTestingListForBulkUpdate.length < 1) {
            this.alertService.warning('audit.auditplan.selectone');
            return;
        }
        this.modalRef = this.bulkUpdateService.openTesting(this.auditTestingListForBulkUpdate);
        this.modalRef.componentInstance.auditTestingListForBulkUpdate = this.auditTestingListForBulkUpdate;
    }

    editTesting(id: any) {
        this.router.navigate(['/audittesting/new/' + id], {});
    }

    checkTesting(event: any, index) {
        if (event.isChecked) {
            this.auditTestingListForBulkUpdate.push(event);
        } else {
            this.auditTestingListForBulkUpdate.splice(index, 1);
        }
    }

    sendAuditeeReminder(id: any) {
        this.auditTestingService.sendAuditeeReminder(id).subscribe(response => {
            console.log('Email sent successfully..!');
            this.alertService.success('audit.auditplan.reminder');
        });
    }

    loadMore() {
        this.page = this.page + 1;
        this.auditTestingService.nextTestingForTestingScreen(this.page, this.searchTestingPlan).subscribe(response => {
            this.auditTestingList = this.auditTestingList.concat(response);
        });
    }
}
