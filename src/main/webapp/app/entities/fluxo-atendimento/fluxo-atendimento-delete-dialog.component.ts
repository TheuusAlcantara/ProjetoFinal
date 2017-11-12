import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FluxoAtendimento } from './fluxo-atendimento.model';
import { FluxoAtendimentoPopupService } from './fluxo-atendimento-popup.service';
import { FluxoAtendimentoService } from './fluxo-atendimento.service';

@Component({
    selector: 'jhi-fluxo-atendimento-delete-dialog',
    templateUrl: './fluxo-atendimento-delete-dialog.component.html'
})
export class FluxoAtendimentoDeleteDialogComponent {

    fluxoAtendimento: FluxoAtendimento;

    constructor(
        private fluxoAtendimentoService: FluxoAtendimentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fluxoAtendimentoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fluxoAtendimentoListModification',
                content: 'Deleted an fluxoAtendimento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fluxo-atendimento-delete-popup',
    template: ''
})
export class FluxoAtendimentoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fluxoAtendimentoPopupService: FluxoAtendimentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fluxoAtendimentoPopupService
                .open(FluxoAtendimentoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
