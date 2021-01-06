import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuditProcedureService } from './auditprocedure.service';
import { AuditProcedure } from 'app/core/audit/auditprocedure.model';
import { AuditObjective } from 'app/core/audit/auditobjective.model';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-audit-proc-update',
    templateUrl: './auditprocedure-objective.component.html'
})
export class AuditProcedureObjectiveComponent implements OnInit {
    id: any;
    auditProcedure: AuditProcedure;
    editObjectiveData: AuditProcedure;
    constructor(route: ActivatedRoute, private alertService: JhiAlertService, private auditProcedureService: AuditProcedureService) {
        route.params.subscribe(params => (this.id = params.id));
    }
    ngOnInit() {
        this.loadAll();
    }

    loadAll() {
        this.auditProcedureService.queryProceduresById(this.id).subscribe(auditProcedure => {
            this.auditProcedure = auditProcedure;
        });
    }

    deleteObjective(id: any) {
        this.auditProcedureService.deleteAuditObjective(id).subscribe(response => this.loadAll());
    }

    previousState() {
        window.history.back();
    }

    editForm(objective?: AuditObjective) {
        objective.isEdit = true;
    }
    cancelForm(objective?: AuditObjective) {
        objective.isEdit = false;
    }

    saveObjective(objective?: AuditObjective) {
        objective.isEdit = false;
        this.auditProcedureService
            .updateObjectiveData(objective)
            .subscribe(response => this.onSaveSuccess(response, objective), () => this.onSaveError(objective));
    }

    onSaveSuccess(data?: any, objective?: AuditObjective) {
        this.alertService.success('audit.procedure.objectiveSuccess');
        objective.isEdit = false;
        this.previousState();
    }

    onSaveError(data?: any) {
        this.alertService.error('audit.procedure.objectiveError');
        data.isEdit = false;
    }
}
