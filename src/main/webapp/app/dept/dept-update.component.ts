import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DeptService } from '../dept/dept.service';
import { JhiLanguageHelper } from 'app/core';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';

@Component({
    selector: 'jhi-dept-update',
    templateUrl: './dept-update.component.html'
})
export class DeptUpdateComponent implements OnInit {
    isSaving: boolean;
    deptList: any;
    dept: any;
    id: any;

    constructor(
        private languageHelper: JhiLanguageHelper,
        private route: ActivatedRoute,
        private router: Router,
        private deptService: DeptService,
        private alertService: JhiAlertService
    ) {
        route.params.subscribe(params => (this.id = params.id));
    }

    ngOnInit() {
        this.isSaving = false;
        this.dept = { isActive: true, id: 0 };
        if (this.id != 0) {
            this.deptService.getDeptById(this.id).subscribe(dept => {
                this.dept = dept;
                this.dept.id = this.id;
            });
        }
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dept.id !== null && this.dept.id !== 0) {
            this.deptService.update(this.dept).subscribe(
                response => {
                    this.alertService.success('audit.deptMaster.updateMsg');
                    this.previousState();
                },
                () => this.onSaveError()
            );
        } else {
            this.deptService.create(this.dept).subscribe(
                response => {
                    this.alertService.success('audit.deptMaster.createMsg');
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
