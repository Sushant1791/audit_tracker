import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IAuditPlan } from 'app/core/audit/auditplan.model';
import { IDepartmentModel } from 'app/core/audit/department.model';
import { AuditProcedure, IAuditProcedure } from 'app/core/audit/auditprocedure.model';
import { ICostCentre } from 'app/core/audit/costCentre.model';

@Injectable({ providedIn: 'root' })
export class AuditPlanService {
    public resourceUrl = SERVER_API_URL + 'api/auditplan';
    constructor(private http: HttpClient) {}

    get(key: string): Observable<any> {
        return this.http.get(this.resourceUrl, {
            params: new HttpParams().set('key', key)
        });
    }

    query(req?: any): Observable<HttpResponse<IAuditPlan[]>> {
        return this.http.get<IAuditPlan[]>(this.resourceUrl + '/auditplans', { observe: 'response' });
    }

    queryAuditorPlans(): Observable<HttpResponse<IAuditPlan[]>> {
        return this.http.get<IAuditPlan[]>(this.resourceUrl + '/user/auditplans', { observe: 'response' });
    }

    getAllAuditPlans(page: any, auditPlan: IAuditPlan) {
        const formdata: FormData = new FormData();
        formdata.append('searchAuditPlan', JSON.stringify(auditPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/auditplan/get/allplans', formdata);
    }

    queryDepartments(): Observable<IDepartmentModel[]> {
        return this.http.get<IDepartmentModel[]>(SERVER_API_URL + 'api/getAllDepartment');
    }

    queryCostCentre(): Observable<ICostCentre[]> {
        return this.http.get<ICostCentre[]>(SERVER_API_URL + 'api/getAllCostCentre');
    }

    createAuditPlan(audit: IAuditPlan): Observable<HttpResponse<IAuditPlan>> {
        return this.http.post<IAuditPlan>(this.resourceUrl + '/createplan', audit, { observe: 'response' });
    }

    queryProcedureList(): Observable<IAuditProcedure[]> {
        return this.http.get<IAuditProcedure[]>(SERVER_API_URL + 'api/getAllProcedure');
    }

    queryPlanById(id?: any): Observable<IAuditPlan> {
        return this.http.get<IAuditPlan>(this.resourceUrl + '/getPlanById/' + id);
    }

    downLoadCSV(module: string): Observable<any> {
        return this.http.get(SERVER_API_URL + 'api/file/download/' + module, { responseType: 'blob' });
    }

    bulkUpdateAuditPlans(auditPlans: IAuditPlan[]): Observable<HttpResponse<IAuditPlan>> {
        return this.http.post(this.resourceUrl + '/bulkupdate', auditPlans, { observe: 'response' });
    }

    getAllObjectives(page: any, auditProcedure: IAuditProcedure): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchProcedure', JSON.stringify(auditProcedure));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/entity/get/all', formdata);
    }

    nextObjective(page: any, auditProcedure: IAuditProcedure): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchProcedure', JSON.stringify(auditProcedure));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/entity/get/next', formdata);
    }

    sendReminder(id: any): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + 'api/auditplan/reminder/' + id);
    }

    //--------------------------
    getAllPlanNew(page: any, searchPlan: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchPlan', JSON.stringify(searchPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/allPlanNew', formdata);
    }

    nextPlanNew(page: any, searchPlan: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchPlan', JSON.stringify(searchPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/nextPlanNew', formdata);
    }
}
