import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationService } from '../location/location.service';
import { JhiLanguageHelper } from 'app/core';
import { JhiAlertService } from 'ng-jhipster/src/service/alert.service';

@Component({
    selector: 'jhi-location-update',
    templateUrl: './location-update.component.html'
})
export class LocationUpdateComponent implements OnInit {
    isSaving: boolean;
    locationList: any;
    location: any;
    id: any;

    constructor(
        private languageHelper: JhiLanguageHelper,
        private route: ActivatedRoute,
        private router: Router,
        private locationService: LocationService,
        private alertService: JhiAlertService
    ) {
        route.params.subscribe(params => (this.id = params.id));
    }

    ngOnInit() {
        this.isSaving = false;
        this.location = { isActive: true, id: 0 };
        if (this.id != 0) {
            this.locationService.getLocationById(this.id).subscribe(location => {
                this.location = location;
                this.location.id = this.id;
            });
        }
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== null && this.location.id !== 0) {
            this.locationService.update(this.location).subscribe(
                response => {
                    this.alertService.success('audit.locationMaster.updateMsg');
                    this.previousState();
                },
                () => this.onSaveError()
            );
        } else {
            this.locationService.create(this.location).subscribe(
                response => {
                    this.alertService.success('audit.locationMaster.createMsg');
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
