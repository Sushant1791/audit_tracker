import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LoginModalService, Principal, Account, AuditPlan } from 'app/core';
import { AuditTestingService } from 'app/audit/testing/audittesting.service';
import { AuditTesting } from 'app/core/audit/auditTesting.model';
import { AuditPlanService, AuditProcedureService } from 'app/audit';
import { HttpResponse } from '@angular/common/http';
import { AuditProcedure } from 'app/core/audit/auditprocedure.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    fullName: any;
    queriesRaisedByAuditor: AuditTesting[];
    queriesForAuditee: AuditTesting[];
    auditplans: AuditPlan[];
    auditProcedureList: AuditProcedure[];
    isAdmin: boolean;
    counts: any;

    constructor(
        private auditTestingService: AuditTestingService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private auditPlanService: AuditPlanService,
        private alertService: JhiAlertService
    ) {
        this.loadDashboard();
    }

    ngOnInit() {
        this.isAdmin = false;
        this.principal.identity().then(account => {
            this.account = account;
            if (this.account.authorities[0] == 'ROLE_ADMIN') {
                this.isAdmin = true;
            } else if (this.account.authorities[1] == 'ROLE_ADMIN') {
                this.isAdmin = true;
            }
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
                this.loadDashboard();
            });
        });
    }

    loadDashboard() {
        this.queriesRaisedByMe();
        this.queriesForAuditees();
        this.loadAuditPlanList();
        this.loadAllEntites();
        this.counts = this.auditTestingService.getDashboardCounts().subscribe(counts => {
            this.counts = counts;
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    queriesRaisedByMe() {
        this.auditTestingService.queryAuditeeReport('Auditor').subscribe(auditTestingList => {
            this.queriesRaisedByAuditor = auditTestingList;
            this.queriesRaisedByAuditor.forEach(element => {
                element.showObjectives = false;
                element.overDueDays = element.expectedRevertDate
                    ? Math.ceil(Math.abs(new Date().getTime() - new Date(element.expectedRevertDate).getTime()) / (1000 * 3600 * 24))
                    : 0;
            });
        });
    }

    loadAllEntites() {
        this.auditPlanService.queryProcedureList().subscribe(auditProcedureList => {
            this.auditProcedureList = auditProcedureList;
        });
    }
    loadAuditPlanList() {
        this.auditPlanService
            .query({
                page: 1,
                size: 5,
                sort: ['createdDate', 'desc']
            })
            .subscribe(
                (res: HttpResponse<AuditPlan[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    private onSuccess(data, headers) {
        this.auditplans = data;
    }

    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    queriesForAuditees() {
        this.auditTestingService.queryAuditeeReport('Auditee').subscribe(auditTestingList => {
            this.queriesForAuditee = auditTestingList;
            this.queriesForAuditee.forEach(element => {
                element.showObjectives = false;
                element.overDueDays = element.expectedRevertDate
                    ? Math.ceil(Math.abs(new Date().getTime() - new Date(element.expectedRevertDate).getTime()) / (1000 * 3600 * 24))
                    : 0;
            });
        });
    }
    login() {
        this.modalRef = this.loginModalService.open();
    }
}
