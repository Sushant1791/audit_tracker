import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { JhiLanguageHelper, User, UserService } from 'app/core';

@Component({
    selector: 'jhi-user-mgmt-update',
    templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
    user: User;
    languages: any[];
    authorities: any[];
    selected_authorities: any;
    isSaving: boolean;
    isAuditor: boolean = false;
    locationList: any;
    userList: any[];

    constructor(
        private languageHelper: JhiLanguageHelper,
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.selected_authorities = '';
        this.route.data.subscribe(({ user }) => {
            this.user = user.body ? user.body : user;
            if (this.user.reporting_user_id) {
                this.isAuditor = true;
            }
        });
        this.authorities = [];
        this.userService.authorities().subscribe(authorities => {
            this.authorities = authorities;
        });
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });
        this.userService.querylocations().subscribe(response => {
            this.locationList = response;
        });
        this.userService.queryusers().subscribe(response => {
            this.userList = [];
            let temp = response;
            if (temp) {
                for (var i = 0; i < temp.length; i++) {
                    if (
                        parseInt(temp[i].id) === 1 ||
                        parseInt(temp[i].id) === 2 ||
                        parseInt(temp[i].id) === 3 ||
                        parseInt(temp[i].id) === 4
                    ) {
                    } else {
                        this.userList.push(temp[i]);
                    }
                }
            }
            //console.log(JSON.stringify(this.userList));
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.user.login = this.user.email;
        this.selected_authorities = this.user.authorities;
        this.user.authorities = [];
        this.user.authorities.push(this.selected_authorities);
        if (this.user.id !== null) {
            this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        } else {
            this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
        this.user.authorities = this.selected_authorities;
    }
}
