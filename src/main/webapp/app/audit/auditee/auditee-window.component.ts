import { Component, OnInit } from '@angular/core';
import { AuditTesting } from 'app/core/audit/auditTesting.model';
import { AuditTestingService } from '../testing/audittesting.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-auditee-window',
    templateUrl: './auditee-window.component.html'
})
export class AuditeeWindowComponent implements OnInit {
    auditTestingList: AuditTesting[];
    date: Date;
    showObj: boolean;
    page: any;
    totalCount: any;
    searchTestingPlan: any;
    constructor(private auditTestingService: AuditTestingService, private router: Router) {}

    ngOnInit(): void {
        this.searchTestingPlan = { auditPlanEntity: '', start_date: '', end_date: '', status: '', respond: '' };
        this.totalCount = 0;
        this.page = 0;
        this.auditTestingList = [];
        this.getAllTestingPlans(this.page, this.searchTestingPlan);
        //this.loadAll();
        this.date = new Date();
    }

    getAllTestingPlans(page, searchTestingPlan) {
        this.page = page;
        console.log('searchTestingPlan : ', searchTestingPlan);
        console.log('this.page : ', this.page);
        this.auditTestingService.getAllTesting(page, searchTestingPlan).subscribe(response => {
            this.auditTestingList = response['list'];
            this.auditTestingList.forEach(element => {
                element.showObjectives = false;
                element.overDueDays = element.expectedRevertDate
                    ? Math.ceil(Math.abs(new Date().getTime() - new Date(element.expectedRevertDate).getTime()) / (1000 * 3600 * 24))
                    : 0;
            });
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
        });
    }

    loadMore() {
        this.page = this.page + 1;
        this.auditTestingService.nextTesting(this.page, this.searchTestingPlan).subscribe(response => {
            this.auditTestingList = this.auditTestingList.concat(response);
            this.auditTestingList.forEach(element => {
                element.showObjectives = false;
                element.overDueDays = element.expectedRevertDate
                    ? Math.ceil(Math.abs(new Date().getTime() - new Date(element.expectedRevertDate).getTime()) / (1000 * 3600 * 24))
                    : 0;
            });
        });
    }

    responseToAudit(id: any) {
        this.router.navigate(['/auditee-update/' + id], {});
    }

    loadAll() {
        this.auditTestingService.queryAuditeeReport('Auditee').subscribe(auditTestingList => {
            console.log('************************************* auditTestingList : ', auditTestingList);
            this.auditTestingList = auditTestingList;
            this.auditTestingList.forEach(element => {
                element.showObjectives = false;
                element.overDueDays = element.expectedRevertDate
                    ? Math.ceil(Math.abs(new Date().getTime() - new Date(element.expectedRevertDate).getTime()) / (1000 * 3600 * 24))
                    : 0;
            });
        });
    }

    showObjective(i) {
        this.auditTestingList[i].showObjectives = this.auditTestingList[i].showObjectives ? false : true;
    }

    previousState() {
        window.history.back();
    }
}
