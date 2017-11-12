import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Agendamento } from './agendamento.model';
import { AgendamentoPopupService } from './agendamento-popup.service';
import { AgendamentoService } from './agendamento.service';

@Component({
    selector: 'jhi-agendamento-dialog',
    templateUrl: './agendamento-dialog.component.html'
})
export class AgendamentoDialogComponent implements OnInit {

    agendamento: Agendamento;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private agendamentoService: AgendamentoService,
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
        if (this.agendamento.id !== undefined) {
            this.subscribeToSaveResponse(
                this.agendamentoService.update(this.agendamento));
        } else {
            this.subscribeToSaveResponse(
                this.agendamentoService.create(this.agendamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<Agendamento>) {
        result.subscribe((res: Agendamento) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Agendamento) {
        this.eventManager.broadcast({ name: 'agendamentoListModification', content: 'OK'});
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
    selector: 'jhi-agendamento-popup',
    template: ''
})
export class AgendamentoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agendamentoPopupService: AgendamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agendamentoPopupService
                    .open(AgendamentoDialogComponent as Component, params['id']);
            } else {
                this.agendamentoPopupService
                    .open(AgendamentoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
