import { Component, OnInit } from '@angular/core';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { DeptService } from '../dept/dept.service';
import { HttpResponse } from '@angular/common/http';
import { Principal } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
    selector: 'jhi-dept',
    templateUrl: './dept.component.html',
    styleUrls: ['dept.css']
})
export class DeptComponent implements OnInit {
    page: any;
    searchDept: any;
    totalCount: any;
    depts: any[];
    currentAccount: any;

    constructor(
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private deptService: DeptService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.searchDept = { id: '0' };
            this.totalCount = 0;
            this.page = 0;
            this.getAllDepts(this.page, this.searchDept);
            console.log('dept : ', this.depts);
            this.currentAccount = account;
            this.registerChangeInDept();
        });
    }

    registerChangeInDept() {
        this.eventManager.subscribe('deptListModification', response => this.getAllDepts(this.page, this.searchDept));
        this.getAllDepts(this.page, this.searchDept);
    }

    editDept(id: any) {
        this.router.navigate(['/department/new/' + id], {});
    }

    getAllDepts(page, job) {
        this.page = page;
        this.deptService.getAllDepts(page, job).subscribe(response => {
            this.depts = response['list'];
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
        });
    }

    loadMore() {
        this.page = this.page + 1;
        this.deptService.nextDepts(this.page, this.searchDept).subscribe(response => {
            this.depts = this.depts.concat(response);
        });
    }
}
