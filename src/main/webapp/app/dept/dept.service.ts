import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class DeptService {
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private http: HttpClient) {}

    create(dept: any): Observable<HttpResponse<any>> {
        return this.http.post<any>(SERVER_API_URL + 'api/dept', dept, { observe: 'response' });
    }

    update(dept: any): Observable<HttpResponse<any>> {
        return this.http.put<any>(SERVER_API_URL + 'api/dept', dept, { observe: 'response' });
    }

    getDeptById(id?: any): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + '/api/dept/' + id);
    }

    getAllDepts(page: any, dept: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchDept', JSON.stringify(dept));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/dept/all', formdata);
    }

    nextDepts(page: any, dept: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchDept', JSON.stringify(dept));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/dept/next', formdata);
    }
}
