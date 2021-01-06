import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuditTestingService } from '../testing/audittesting.service';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';
import * as jsPDF from 'jspdf';
import * as html2canvas from 'html2canvas';
import { UserService } from 'app/core/user/user.service';

@Component({
    selector: 'jhi-audit-report-generate',
    templateUrl: './audit-report-generate.component.html'
})
export class AuditReportGenerateComponent implements OnInit {
    department?: any;
    month?: any;
    year?: any;
    data?: any;
    doc?: any;
    location?: any;
    locationList?: any;
    locationNew?: any;

    constructor(
        private router: Router,
        private auditTestingService: AuditTestingService,
        route: ActivatedRoute,
        private userService: UserService,
        private alertService: JhiAlertService
    ) {
        route.params.subscribe(
            params => (
                (this.department = params.department),
                (this.month = params.month),
                (this.year = params.year),
                (this.location = params.location)
            )
        );
    }
    ngOnInit(): void {
        this.userService.querylocations().subscribe(response => {
            this.locationList = response;
            this.locationNew = this.locationList.filter(location => location.id == this.location);
        });
        this.auditTestingService.queryAuditReport(this.department, this.month, this.year, this.location).subscribe(response => {
            this.data = response;
        });
    }
    generatePDF() {
        let doc = new jsPDF();
        doc.setFontSize(20);
        doc;
        doc.text(100, 10, 'Generated Audit Report');

        // Create your table here (The dynamic table needs to be converted to canvas).
        let element = document.getElementById('report');
        // html2canvas(element).then((canvas: any) => {
        //     doc.addImage(canvas.toDataURL('image/jpeg'), 'JPEG', 0, 20, doc.internal.pageSize.width, element.offsetHeight / 5);
        //     doc.save('Audit-Report.pdf');
        // });
    }
}
