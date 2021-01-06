import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CostCenterService } from '../costcenter/costcenter.service';
import { JhiLanguageHelper } from 'app/core';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';

@Component({
    selector: 'jhi-costcenter-update',
    templateUrl: './costcenter-update.component.html'
})
export class CostCentreUpdateComponent implements OnInit {
    isSaving: boolean;
    costcenter: any;
    id: any;

    constructor(
        private languageHelper: JhiLanguageHelper,
        private route: ActivatedRoute,
        private router: Router,
        private costCenterService: CostCenterService,
        private alertService: JhiAlertService
    ) {
        route.params.subscribe(params => (this.id = params.id));
    }

    ngOnInit() {
        this.isSaving = false;
        this.costcenter = { isActive: true, id: 0 };
        if (this.id != 0) {
            this.costCenterService.getCostCentreById(this.id).subscribe(costcenter => {
                this.costcenter = costcenter;
                this.costcenter.id = this.id;
            });
        }
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.costcenter.id !== null && this.costcenter.id !== 0) {
            this.costCenterService.update(this.costcenter).subscribe(
                response => {
                    this.alertService.success('audit.costCenterMaster.updateMsg');
                    this.previousState();
                },
                () => this.onSaveError()
            );
        } else {
            this.costCenterService.create(this.costcenter).subscribe(
                response => {
                    this.alertService.success('audit.costCenterMaster.createMsg');
                    this.previousState();
                },
                () => this.onSaveError()
            );
        }
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
