import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UBS } from './ubs.model';
import { UBSPopupService } from './ubs-popup.service';
import { UBSService } from './ubs.service';

@Component({
    selector: 'jhi-ubs-delete-dialog',
    templateUrl: './ubs-delete-dialog.component.html'
})
export class UBSDeleteDialogComponent {

    uBS: UBS;

    constructor(
        private uBSService: UBSService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.uBSService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'uBSListModification',
                content: 'Deleted an uBS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ubs-delete-popup',
    template: ''
})
export class UBSDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uBSPopupService: UBSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.uBSPopupService
                .open(UBSDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
