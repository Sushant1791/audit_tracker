import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IAuditProcedure, AuditProcedure } from 'app/core/audit/auditprocedure.model';

import { SERVER_API_URL } from 'app/app.constants';
import { AuditObjective } from 'app/core/audit/auditobjective.model';

@Injectable({ providedIn: 'root' })
export class AuditProcedureService {
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };
    constructor(private http: HttpClient) {}

    queryProcedures(req?: any): Observable<HttpResponse<IAuditProcedure[]>> {
        return this.http.get<IAuditProcedure[]>(SERVER_API_URL + '/gatAllProcedure', { observe: 'response' });
    }

    createAuditProcedure(auditProcedure: IAuditProcedure): Observable<IAuditProcedure> {
        return this.http.post<IAuditProcedure>(SERVER_API_URL + '/api/createProcedure', JSON.stringify(auditProcedure), this.httpOptions);
    }

    queryProceduresById(id?: any): Observable<IAuditProcedure> {
        return this.http.get<IAuditProcedure>(SERVER_API_URL + '/api/getObjectiveById/' + id);
    }

    deleteAuditProcedure(id?: any): Observable<IAuditProcedure> {
        return this.http.get<IAuditProcedure>(SERVER_API_URL + '/api/deleteProcedure/' + id);
    }

    deleteAuditObjective(id?: any): Observable<IAuditProcedure> {
        return this.http.get<IAuditProcedure>(SERVER_API_URL + '/api/deleteObjective/' + id);
    }

    getAllUsers(user: any): Observable<any> {
        let formdata: FormData = new FormData();
        formdata.append('searchUser', JSON.stringify(user));

        return this.http.post(SERVER_API_URL + 'api/get/allProcedure', formdata);
    }

    bulkUpdateProcedure(data: AuditProcedure[]) {
        return this.http.post(SERVER_API_URL + 'api/auditobjective/bulkupdate', data);
    }

    updateObjectiveData(data: AuditObjective) {
        return this.http.post(SERVER_API_URL + 'api/auditObjective/update', data);
    }
}
