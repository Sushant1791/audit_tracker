import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class LocationService {
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private http: HttpClient) {}

    create(location: any): Observable<HttpResponse<any>> {
        return this.http.post<any>(SERVER_API_URL + 'api/location', location, { observe: 'response' });
    }

    update(location: any): Observable<HttpResponse<any>> {
        return this.http.put<any>(SERVER_API_URL + 'api/location', location, { observe: 'response' });
    }

    getLocationById(id?: any): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + '/api/location/' + id);
    }

    getAllLocations(page: any, location: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchLocation', JSON.stringify(location));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/locations/all', formdata);
    }

    nextLocations(page: any, location: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchLocation', JSON.stringify(location));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/locations/next', formdata);
    }
}
