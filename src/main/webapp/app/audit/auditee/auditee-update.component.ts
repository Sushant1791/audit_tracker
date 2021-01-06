import { Component, OnInit } from '@angular/core';
import { AuditTestingService } from '../testing/audittesting.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuditTesting } from 'app/core/audit/auditTesting.model';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-auditee-update',
    templateUrl: './auditee-update.component.html'
})
export class AuditeeUpdateComponent implements OnInit {
    id: any;
    auditTesting: AuditTesting;
    testingResultList: any;
    ratings: any;
    auditeeResponse: any;
    responseData: AuditTesting;
    selectedFiles: FileList;

    constructor(
        private router: Router,
        private auditTestingService: AuditTestingService,
        route: ActivatedRoute,
        private alertService: JhiAlertService
    ) {
        route.params.subscribe(params => (this.id = params.id));
    }

    ngOnInit(): void {
        this.auditTesting = {};
        this.responseData = {};
        this.auditTestingService.queryTestingPlanById(this.id).subscribe(auditTesting => {
            this.onSuccessResponse(auditTesting), () => this.onSaveError();
        });

        this.testingResultList = [
            { id: 1, value: 'Planned' },
            { id: 2, value: 'In Process' },
            { id: 3, value: 'Non Compliance' },
            { id: 4, value: 'Complied' },
            { id: 5, value: 'For Information Only' },
            { id: 6, value: 'Others (Type)' }
        ];
        this.ratings = ['Critical', 'Non-critical'];
    }
    previousState() {
        window.history.back();
    }

    change(event: any) {
        if (event.target.files && event.target.files[0]) {
            this.selectedFiles = event.target.files;
        }
    }
    onSuccessResponse(auditTesting) {
        this.auditTesting = auditTesting;
        this.auditTesting.testResult = 'In Process';
        this.auditTesting.rating = this.ratings[0];
        this.auditTesting.overDueDays = this.auditTesting.expectedRevertDate
            ? Math.ceil(Math.abs(new Date().getTime() - new Date(this.auditTesting.expectedRevertDate).getTime()) / (1000 * 3600 * 24))
            : 0;
    }

    downloadFileService(file: string, id: any) {
        this.auditTestingService.downFile(file, id).subscribe(data => this.downloadFile(file, data)),
            error => console.log('Error while downloading ' + file),
            () => console.log('OK');
    }

    downloadFile(fileType: string, data: any) {
        const blob = new Blob([data], { type: 'text/' + fileType.split('.')[0] });
        const url = window.URL.createObjectURL(blob);
        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, fileType);
        } else {
            let a = document.createElement('a');
            a.href = url;
            a.download = fileType;
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    }
    onSaveError() {
        console.log('Error Occurred!!');
    }

    submitResponse() {
        const formdata: FormData = new FormData();
        this.responseData.id = this.auditTesting.id;
        this.responseData.testingPlanObservationData = this.auditTesting.testingPlanObservationData;
        if (this.selectedFiles !== undefined && this.selectedFiles.length > 0) {
            for (let i = 0; i < this.selectedFiles.length; i++) {
                formdata.append('files', this.selectedFiles[i], this.selectedFiles[i].name);
            }
        }
        console.log('Risky' + JSON.stringify(this.responseData));
        formdata.append('auditeeResponse', JSON.stringify(this.responseData));
        this.auditTestingService.submitResponse(formdata).subscribe(response => this.onSaveSuccessResponse(response));
    }

    onSaveSuccessResponse(response: any) {
        this.router.navigateByUrl('auditee-window');
        this.alertService.success('audit.auditee.successMsg');
    }
}
