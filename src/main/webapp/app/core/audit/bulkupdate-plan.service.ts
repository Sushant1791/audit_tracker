import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { JhiBulkUpdatePlanModalComponent } from 'app/shared/auditplan/bulkupdate-plan.component';
import { JhiBulkUpdateTestingModalComponent } from 'app/shared/audit-testing/bulkupdate-testing.component';
import { JhiBulkUpdateEntityModalComponent } from 'app/shared/audit-entity/bulkupdate-entity.component';

@Injectable({ providedIn: 'root' })
export class BulkUpdatePlanModalService {
    private isOpen = false;
    constructor(private modalService: NgbModal) {}

    open(data): NgbModalRef {
        const modalRef = this.modalService.open(JhiBulkUpdatePlanModalComponent);
        modalRef.result.then(
            result => {
                this.isOpen = false;
            },
            reason => {
                this.isOpen = false;
            }
        );
        return modalRef;
    }

    openTesting(data): NgbModalRef {
        const modalRef = this.modalService.open(JhiBulkUpdateTestingModalComponent);
        modalRef.result.then(
            result => {
                this.isOpen = false;
            },
            reason => {
                this.isOpen = false;
            }
        );
        return modalRef;
    }

    openEntity(data): NgbModalRef {
        const modalRef = this.modalService.open(JhiBulkUpdateEntityModalComponent);
        modalRef.result.then(
            result => {
                this.isOpen = false;
            },
            reason => {
                this.isOpen = false;
            }
        );
        return modalRef;
    }
}
