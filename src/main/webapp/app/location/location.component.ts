import { Component, OnInit } from '@angular/core';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { LocationService } from '../location/location.service';
import { HttpResponse } from '@angular/common/http';
import { Principal } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
    selector: 'jhi-location',
    templateUrl: './location.component.html',
    styleUrls: ['location.css']
})
export class LocationComponent implements OnInit {
    page: any;
    searchLocation: any;
    totalCount: any;
    locations: any[];
    currentAccount: any;
    constructor(
        private eventManager: JhiEventManager,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private locationService: LocationService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.searchLocation = { id: '0' };
            this.totalCount = 0;
            this.page = 0;
            this.getAllLocations(this.page, this.searchLocation);
            console.log('location : ', this.locations);
            this.currentAccount = account;
            this.registerChangeInLocation();
        });
    }

    registerChangeInLocation() {
        this.eventManager.subscribe('locationListModification', response => this.getAllLocations(this.page, this.searchLocation));
        this.getAllLocations(this.page, this.searchLocation);
    }

    editLocation(id: any) {
        this.router.navigate(['/locations/new/' + id], {});
    }

    getAllLocations(page, job) {
        this.page = page;
        this.locationService.getAllLocations(page, job).subscribe(response => {
            this.locations = response['list'];
            this.totalCount = Math.ceil(parseInt(response['count']) / 10);
        });
    }

    loadMore() {
        this.page = this.page + 1;
        this.locationService.nextLocations(this.page, this.searchLocation).subscribe(response => {
            this.locations = this.locations.concat(response);
        });
    }
}
