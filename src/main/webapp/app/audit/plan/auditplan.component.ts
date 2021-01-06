import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { AuditPlanService } from './auditplan.service';

import { AuditPlan } from 'app/core/audit/auditplan.model';
import { Principal, User, UserService } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';

import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { BulkUpdatePlanModalService } from 'app/core/audit/bulkupdate-plan.service';

@Component({
    selector: 'jhi-audit-plan',
    templateUrl: './auditplan.component.html'
})
export class AuditPlanComponent implements OnInit, OnDestroy {
    error: string;
    currentAccount: any;
    showobjectId: any;
    success: string;
    modalRef: NgbModalRef;
    auditplans: AuditPlan[];
    userList: User[];
    totalCount: any;
    routeData: any;
    links: any;
    searchaudit: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    auditPlanListForBulkUpdate: AuditPlan[];
    alerts: any[];

    constructor(
        private auditPlanService: AuditPlanService,
        private alertService: JhiAlertService,
        private parseLinks: JhiParseLinks,
        private bulkUpdateModalService: BulkUpdatePlanModalService,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private router: Router,
        private userService: UserService,
        private datePipe: DatePipe
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.searchaudit = { assignTo: '0', start_date: '', end_date: '', remarks: '', auditPlanEntity: '' };
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.totalCount = 0;
            this.page = 0;
            this.getAllPlan(this.page, this.searchaudit);
        });
        this.auditPlanListForBulkUpdate = [];
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
    }

    sort() {
        const result = ['createdDate' + ',' + (this.reverse ? 'asc' : 'desc')];
        return result;
    }
    private onSuccess(data, headers) {
        this.auditplans = data;
    }

    bulkUpdateModal() {
        if (this.auditPlanListForBulkUpdate.length < 1) {
            this.alertService.warning('audit.auditplan.selectone');
            return;
        }
        this.modalRef = this.bulkUpdateModalService.open(this.auditPlanListForBulkUpdate);
        this.modalRef.componentInstance.auditPlanListForBulkUpdate = this.auditPlanListForBulkUpdate;
        this.modalRef.componentInstance.userList = this.userList;
    }
    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    loadMore() {
        this.page = this.page + 1;
        this.auditPlanService.nextPlanNew(this.page, this.searchaudit).subscribe(response => {
            this.auditplans = this.auditplans.concat(response);
        });
    }

    getAllPlan(page, job) {
        this.page = page;
        this.auditPlanService.getAllPlanNew(page, job).subscribe(response => {
            this.auditplans = response['list'];
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
        });
    }

    downLoadCSV() {
        this.auditPlanService.downLoadCSV('AuditPlan').subscribe(data => this.downloadFile(data, 'AuditPlan'));
    }

    downloadFile(data: any, module: string) {
        const blob = new Blob([data], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);
        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, module.toLowerCase + '_' + this.datePipe.transform(new Date(), 'yyyy-MM-dd') + '.csv');
        } else {
            const a = document.createElement('a');
            a.href = url;
            a.download = module.toLowerCase + '_' + this.datePipe.transform(new Date(), 'yyyy-MM-dd') + '.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    }

    checkValue(event: any, index) {
        if (event.isChecked) {
            this.auditPlanListForBulkUpdate.push(event);
        } else {
            this.auditPlanListForBulkUpdate.splice(index, 1);
        }
    }
    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    editPlan(id: any) {
        this.router.navigate(['/auditplan/new/' + id], {});
    }

    sendReminder(id: any) {
        this.auditPlanService.sendReminder(id).subscribe(response => {
            console.log('Email sent successfully..!');
            this.alertService.success('audit.auditplan.reminder');
        });
    }

    previousState() {
        window.history.back();
    }
}
