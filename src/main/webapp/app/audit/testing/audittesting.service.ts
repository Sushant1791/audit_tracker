import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse, HttpHeaders } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { IAuditTesting, AuditTesting } from 'app/core/audit/auditTesting.model';

@Injectable({ providedIn: 'root' })
export class AuditTestingService {
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };
    constructor(private http: HttpClient) {}

    queryAuditTesting(page: any, auditPlan: IAuditTesting): Observable<AuditTesting[]> {
        const formdata: FormData = new FormData();
        formdata.append('searchAuditPlan', JSON.stringify(auditPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post<AuditTesting[]>(SERVER_API_URL + '/api/getAllAuditTestingPlan', formdata);
    }

    deleteAuditTesting(id?: any): Observable<IAuditTesting> {
        return this.http.get<IAuditTesting>(SERVER_API_URL + '/api/deleteAuditTestingPlan/' + id);
    }

    createAuditTesting(formdata: FormData) {
        return this.http.post<IAuditTesting>(SERVER_API_URL + '/api/createAuditTestingPlan', formdata);
    }

    queryTestingPlanById(id?: any): Observable<IAuditTesting> {
        return this.http.get<IAuditTesting>(SERVER_API_URL + '/api/get/testing/plan/' + id);
    }

    bulkUpdateTests(data: AuditTesting[]) {
        return this.http.post(SERVER_API_URL + 'api/audittesting/bulkupdate', data);
    }

    submitResponse(formdata: FormData) {
        return this.http.post(SERVER_API_URL + 'api/update/auditee', formdata);
    }

    sendAuditeeReminder(id: any): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + 'api/testing/auditee/reminder/' + id);
    }

    queryAuditReport(department?: any, month?: any, year?: any, location?: any): Observable<IAuditTesting[]> {
        return this.http.get<IAuditTesting[]>(
            SERVER_API_URL + '/api/auditreport/' + department + '/' + month + '/' + year + '/' + location
        );
    }

    queryAuditeeReport(planFor?: any): Observable<IAuditTesting[]> {
        // return this.http.get<IAuditTesting[]>(SERVER_API_URL + '/api/get/testing/plans/' + planFor);
        return this.http.get<IAuditTesting[]>(SERVER_API_URL + '/api/auditeeResponse');
    }

    queryRiskArea(): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + '/api/get/riskareas');
    }

    queryTestingResult(): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + '/api/get/testingresults');
    }

    queryActionTaken(): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + '/api/get/actiontakens');
    }

    downFile(file?: any, id?: any): Observable<any> {
        return this.http.get(SERVER_API_URL + '/api/downloadFile/' + id + '/' + file, { responseType: 'blob' });
    }

    lockAuditTesting(data: AuditTesting) {
        return this.http.post<AuditTesting>(SERVER_API_URL + '/api/lock/audittesting', data);
    }

    getAllTesting(page: any, searchTestingPlan: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchTestingPlan', JSON.stringify(searchTestingPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/allTestingPlan', formdata);
    }

    nextTesting(page: any, searchTestingPlan: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchTestingPlan', JSON.stringify(searchTestingPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/nextTestingPlan', formdata);
    }

    getAllTestingForTestingScreen(page: any, searchTestingPlan: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchTestingPlan', JSON.stringify(searchTestingPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/allTestingPlanForTestingScreen', formdata);
    }

    nextTestingForTestingScreen(page: any, searchTestingPlan: any): Observable<any> {
        const formdata: FormData = new FormData();
        formdata.append('searchTestingPlan', JSON.stringify(searchTestingPlan));
        formdata.append('page', JSON.stringify(page));
        return this.http.post(SERVER_API_URL + 'api/get/nextTestingPlanForTestingScreen', formdata);
    }

    getDashboardCounts(): Observable<any> {
        return this.http.get<any>(SERVER_API_URL + '/api/testing/dashboard/counts');
    }
}
