import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUser, User } from './user.model';
import { isBuffer } from 'util';
import { IAuditPlan } from '../audit/auditplan.model';

@Injectable({ providedIn: 'root' })
export class UserService {
    public resourceUrl = SERVER_API_URL + 'api/users';

    constructor(private http: HttpClient) {}

    create(user: IUser): Observable<HttpResponse<IUser>> {
        return this.http.post<IUser>(this.resourceUrl, user, { observe: 'response' });
    }

    update(user: IUser): Observable<HttpResponse<IUser>> {
        return this.http.put<IUser>(this.resourceUrl, user, { observe: 'response' });
    }

    find(login: string): Observable<HttpResponse<IUser>> {
        return this.http.get<IUser>(`${this.resourceUrl}/${login}`, { observe: 'response' });
    }

    query(req?: any): Observable<HttpResponse<IUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(login: string): Observable<HttpResponse<any>> {
        return this.http.delete(`${this.resourceUrl}/${login}`, { observe: 'response' });
    }

    authorities(): Observable<string[]> {
        return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities');
    }

    queryusers(): Observable<any> {
        return this.http.get<any>(this.resourceUrl + '/get/list');
    }

    querylocations(): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + 'api/location');
    }

    getAllUsers(page: any, user: IUser): Observable<any> {
        let formdata: FormData = new FormData();
        formdata.append('searchUser', JSON.stringify(user));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/all', formdata);
    }

    nextUsers(page: any, user: IUser): Observable<any> {
        let formdata: FormData = new FormData();
        formdata.append('searchUser', JSON.stringify(user));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/next', formdata);
    }
}
