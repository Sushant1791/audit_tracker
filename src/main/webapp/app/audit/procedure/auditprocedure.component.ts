import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { AuditProcedureService } from './auditprocedure.service';
import { AuditPlanService } from '../plan/auditplan.service';
import { AuditProcedure } from 'app/core/audit/auditprocedure.model';
import { DatePipe } from '@angular/common';
import { BulkUpdatePlanModalService } from 'app/core/audit/bulkupdate-plan.service';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-audit-procedure',
    templateUrl: './auditprocedure.component.html'
})
export class AuditProcedureComponent implements OnInit, OnDestroy {
    error: string;
    success: string;
    modalRef: NgbModalRef;
    auditProcedureList: AuditProcedure[];
    routeData: any;
    searchProcedure: any;
    auditEntityListForBulkUpdate: AuditProcedure[];
    page: any;
    totalCount: any;
    constructor(
        private auditPlanService: AuditPlanService,
        private auditProcedureService: AuditProcedureService,
        private route: ActivatedRoute,
        private datePipe: DatePipe,
        private router: Router,
        private bulkUpdateService: BulkUpdatePlanModalService,
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService
    ) {
        this.routeData = this.route.data.subscribe(data => {});
    }

    ngOnInit() {
        this.searchProcedure = { title: '', description: '' };
        this.auditEntityListForBulkUpdate = [];
        // this.loadAll();
        this.totalCount = 0;
        this.page = 0;
        this.searchProcedure = {};
        this.getIntialProcedures(this.page, this.searchProcedure);
        this.eventManager.subscribe('entityListModification', response => this.getIntialProcedures(this.page, this.searchProcedure));
    }

    bulkUpdateModal() {
        if (this.auditEntityListForBulkUpdate.length < 1) {
            this.alertService.warning('audit.auditplan.selectone');
            return;
        }
        this.modalRef = this.bulkUpdateService.openEntity(this.auditEntityListForBulkUpdate);
        this.modalRef.componentInstance.auditEntityListForBulkUpdate = this.auditEntityListForBulkUpdate;
    }

    loadAll() {
        this.auditPlanService.queryProcedureList().subscribe(auditProcedureList => {
            this.auditProcedureList = auditProcedureList;
        });
    }

    deleteProcedure(id: any) {
        this.auditProcedureService.deleteAuditProcedure(id).subscribe(response => this.loadAll());
    }

    downLoadCSV() {
        const module = 'Procedure';
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
            const a = document.createElement('a');
            a.href = url;
            a.download = module + '_' + this.datePipe.transform(new Date(), 'yyyy-MM-dd') + '.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    editProcedure(id: any) {
        this.router.navigate(['/auditprocedure/new/' + id], {});
    }

    checkProc(event: any, index) {
        if (event.isChecked) {
            this.auditEntityListForBulkUpdate.push(event);
        } else {
            this.auditEntityListForBulkUpdate.splice(index, 1);
        }
        console.log(JSON.stringify(this.auditEntityListForBulkUpdate));
    }
    getAllProcedure(job) {
        // this.page = page;
        this.auditProcedureService.getAllUsers(job).subscribe(auditProcedureList => {
            this.auditProcedureList = auditProcedureList;
        });
    }

    previousState() {
        window.history.back();
    }

    getIntialProcedures(page, job) {
        this.page = page;
        this.auditPlanService.getAllObjectives(page, job).subscribe(response => {
            this.auditProcedureList = response['list'];
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
        });
    }

    loadMore() {
        this.page = this.page + 1;
        this.auditPlanService.nextObjective(this.page, this.searchProcedure).subscribe(response => {
            this.auditProcedureList = this.auditProcedureList.concat(response);
        });
    }
}
