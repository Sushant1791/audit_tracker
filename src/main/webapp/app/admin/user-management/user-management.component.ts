import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared';
import { Principal, UserService, User } from 'app/core';
import { UserMgmtDeleteDialogComponent } from 'app/admin';
import { AuditPlanService } from 'app/audit';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'jhi-user-mgmt',
    templateUrl: './user-management.component.html'
})
export class UserMgmtComponent implements OnInit, OnDestroy {
    currentAccount: any;
    users: User[];
    error: any;
    success: any;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    searchuser: any;
    totalCount: any;
    locationList: any;

    constructor(
        private userService: UserService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private parseLinks: JhiParseLinks,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private modalService: NgbModal,
        private auditPlanService: AuditPlanService,
        private datePipe: DatePipe
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.searchuser = { locationId: '0' };
            this.totalCount = 0;
            this.page = 0;

            this.userService.querylocations().subscribe(response => {
                this.locationList = response;
            });

            this.currentAccount = account;
            this.registerChangeInUsers();
        });
    }

    ngOnDestroy() {
        this.routeData.unsubscribe();
    }

    registerChangeInUsers() {
        this.eventManager.subscribe('userListModification', response => this.getAllUsers(this.page, this.searchuser));
        this.getAllUsers(this.page, this.searchuser);
    }

    setActive(user, isActivated) {
        user.activated = isActivated;

        this.userService.update(user).subscribe(response => {
            if (response.status === 200) {
                this.error = null;
                this.success = 'OK';
                this.loadAll();
            } else {
                this.success = null;
                this.error = 'ERROR';
            }
        });
    }

    loadAll() {
        this.userService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<User[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpResponse<any>) => this.onError(res.body)
            );
    }

    trackIdentity(index, item: User) {
        return item.id;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/admin/user-management'], {
            queryParams: {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    deleteUser(user: User) {
        const modalRef = this.modalService.open(UserMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.user = user;
        modalRef.result.then(
            result => {
                // Left blank intentionally, nothing to do here
            },
            reason => {
                // Left blank intentionally, nothing to do here
            }
        );
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        this.users = data;
    }

    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    getAllUsers(page, job) {
        this.page = page;
        this.userService.getAllUsers(page, job).subscribe(response => {
            this.users = [];
            let temp = response['list'];
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
            if (temp) {
                for (var i = 0; i < temp.length; i++) {
                    if (parseInt(temp[i].id) == 1 || parseInt(temp[i].id) == 2 || parseInt(temp[i].id) == 3 || parseInt(temp[i].id) == 4) {
                    } else {
                        this.users.push(temp[i]);
                    }
                }
            }
        });
    }

    downLoadCSV() {
        let module = 'User';
        this.auditPlanService.downLoadCSV(module).subscribe(data => this.downloadFile(data, module)),
            error => console.log('Error downloading the file.'),
            () => console.info('OK');
    }

    downloadFile(data: any, module: string) {
        const blob = new Blob([data], { type: 'text/csv' });
        console.log(blob);
        const url = window.URL.createObjectURL(blob);
        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, module + '_' + this.datePipe.transform(new Date(), 'yyyy-MM-dd') + '.csv');
        } else {
            let a = document.createElement('a');
            a.href = url;
            a.download = module + '_' + this.datePipe.transform(new Date(), 'yyyy-MM-dd') + '.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    }

    loadMore() {
        this.page = this.page + 1;
        this.userService.nextUsers(this.page, this.searchuser).subscribe(response => {
            this.users = this.users.concat(response);
        });
    }
}
