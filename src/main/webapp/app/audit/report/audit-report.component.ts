import { Component, OnInit } from '@angular/core';
import { DepartmentModel } from 'app/core/audit/department.model';
import { AuditPlanService } from '../plan/auditplan.service';
import { AuditTestingService } from '../testing/audittesting.service';
import { Router } from '@angular/router';
import { UserService } from 'app/core/user/user.service';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';

@Component({
    selector: 'jhi-audit-report',
    templateUrl: './audit-report.component.html'
})
export class AuditReportComponent implements OnInit {
    months: any;
    date: any;
    currentYear: any;
    years: any;
    departmentList: DepartmentModel[];
    orderReport: any;
    locationList: any;

    constructor(
        private auditplanService: AuditPlanService,
        private auditTestingService: AuditTestingService,
        private userService: UserService,
        private router: Router,
        private alertService: JhiAlertService
    ) {}

    ngOnInit(): void {
        this.orderReport = { month: 0, year: 0, department: 0, location: 0 };
        this.years = [];
        this.getYear();
        this.months = [
            { id: 1, month: 'January' },
            { id: 2, month: 'February' },
            { id: 3, month: 'March' },
            { id: 4, month: 'April' },
            { id: 5, month: 'May' },
            { id: 6, month: 'June' },
            { id: 7, month: 'July' },
            { id: 8, month: 'August' },
            { id: 9, month: 'September' },
            { id: 10, month: 'October' },
            { id: 11, month: 'November' },
            { id: 12, month: 'December' }
        ];

        this.auditplanService.queryDepartments().subscribe(departmentList => {
            this.departmentList = departmentList;
        });

        this.userService.querylocations().subscribe(response => {
            this.locationList = response;
        });
    }

    previousState() {
        window.history.back();
    }

    getYear() {
        this.date = new Date();
        this.currentYear = this.date.getFullYear();
        for (let i = 0; i <= 100; i++) {
            this.years.push(this.currentYear - 1 + i);
        }
    }

    generateReport() {
        if (this.orderReport.department == 0) {
            this.alertService.warning('audit.testing.selectDept');
            return;
        }
        if (this.orderReport.location == 0) {
            this.alertService.warning('audit.testing.selectLocation');
            return;
        }
        if (this.orderReport.month == 0) {
            this.alertService.warning('audit.testing.selectMonth');
            return;
        }
        if (this.orderReport.year == 0) {
            this.alertService.warning('audit.testing.selectYear');
            return;
        }

        this.router.navigate(
            [
                '/audit-report-generate/' +
                    this.orderReport.department +
                    '/' +
                    this.orderReport.month +
                    '/' +
                    this.orderReport.year +
                    '/' +
                    this.orderReport.location
            ],
            {}
        );
    }
}
