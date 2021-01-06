import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class CostCenterService {
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private http: HttpClient) {}

    create(costCentre: any): Observable<HttpResponse<any>> {
        return this.http.post<any>(SERVER_API_URL + 'api/costCentre', costCentre, { observe: 'response' });
    }

    update(costCentre: any): Observable<HttpResponse<any>> {
        return this.http.put<any>(SERVER_API_URL + 'api/costCentre', costCentre, { observe: 'response' });
    }

    getCostCentreById(id?: any): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + '/api/costCentre/' + id);
    }

    getAllCostCentres(page: any, costCentre: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchCostCentre', JSON.stringify(costCentre));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/costCentre/all', formdata);
    }

    nextCostCentres(page: any, costCentre: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchCostCentre', JSON.stringify(costCentre));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/costCentre/next', formdata);
    }
}
