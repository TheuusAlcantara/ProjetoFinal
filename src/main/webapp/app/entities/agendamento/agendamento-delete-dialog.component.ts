import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Agendamento } from './agendamento.model';
import { AgendamentoPopupService } from './agendamento-popup.service';
import { AgendamentoService } from './agendamento.service';

@Component({
    selector: 'jhi-agendamento-delete-dialog',
    templateUrl: './agendamento-delete-dialog.component.html'
})
export class AgendamentoDeleteDialogComponent {

    agendamento: Agendamento;

    constructor(
        private agendamentoService: AgendamentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agendamentoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'agendamentoListModification',
                content: 'Deleted an agendamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-agendamento-delete-popup',
    template: ''
})
export class AgendamentoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agendamentoPopupService: AgendamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.agendamentoPopupService
                .open(AgendamentoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
