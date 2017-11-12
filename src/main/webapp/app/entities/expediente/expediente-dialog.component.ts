import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Expediente } from './expediente.model';
import { ExpedientePopupService } from './expediente-popup.service';
import { ExpedienteService } from './expediente.service';

@Component({
    selector: 'jhi-expediente-dialog',
    templateUrl: './expediente-dialog.component.html'
})
export class ExpedienteDialogComponent implements OnInit {

    expediente: Expediente;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private expedienteService: ExpedienteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.expediente.id !== undefined) {
            this.subscribeToSaveResponse(
                this.expedienteService.update(this.expediente));
        } else {
            this.subscribeToSaveResponse(
                this.expedienteService.create(this.expediente));
        }
    }

    private subscribeToSaveResponse(result: Observable<Expediente>) {
        result.subscribe((res: Expediente) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Expediente) {
        this.eventManager.broadcast({ name: 'expedienteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-expediente-popup',
    template: ''
})
export class ExpedientePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private expedientePopupService: ExpedientePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.expedientePopupService
                    .open(ExpedienteDialogComponent as Component, params['id']);
            } else {
                this.expedientePopupService
                    .open(ExpedienteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
