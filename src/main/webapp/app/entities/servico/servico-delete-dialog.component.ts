import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Servico } from './servico.model';
import { ServicoPopupService } from './servico-popup.service';
import { ServicoService } from './servico.service';

@Component({
    selector: 'jhi-servico-delete-dialog',
    templateUrl: './servico-delete-dialog.component.html'
})
export class ServicoDeleteDialogComponent {

    servico: Servico;

    constructor(
        private servicoService: ServicoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.servicoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'servicoListModification',
                content: 'Deleted an servico'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-servico-delete-popup',
    template: ''
})
export class ServicoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private servicoPopupService: ServicoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.servicoPopupService
                .open(ServicoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
