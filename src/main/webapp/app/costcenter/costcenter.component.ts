import { Component, OnInit } from '@angular/core';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { CostCenterService } from '../costcenter/costcenter.service';
import { HttpResponse } from '@angular/common/http';
import { Principal } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
    selector: 'jhi-costcentre',
    templateUrl: './costcenter.component.html',
    styleUrls: ['costcenter.css']
})
export class CostCentreComponent implements OnInit {
    page: any;
    searchCostCenter: any;
    totalCount: any;
    costcenters: any[];
    currentAccount: any;

    constructor(
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private costCenterService: CostCenterService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.searchCostCenter = {};
            this.totalCount = 0;
            this.page = 0;
            this.getAllCostCenters(this.page, this.searchCostCenter);
            this.currentAccount = account;
            this.registerChangeInCostCenter();
        });
    }

    registerChangeInCostCenter() {
        this.eventManager.subscribe('costCenterListModification', response => this.getAllCostCenters(this.page, this.searchCostCenter));
    }

    editCostCenter(id: any) {
        this.router.navigate(['/costcentre/new/' + id], {});
    }

    getAllCostCenters(page, job) {
        this.page = page;
        this.costCenterService.getAllCostCentres(page, job).subscribe(response => {
            this.costcenters = response['list'];
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
        });
    }

    loadMore() {
        this.page = this.page + 1;
        this.costCenterService.nextCostCentres(this.page, this.searchCostCenter).subscribe(response => {
            this.costcenters = this.costcenters.concat(response);
        });
    }
}
