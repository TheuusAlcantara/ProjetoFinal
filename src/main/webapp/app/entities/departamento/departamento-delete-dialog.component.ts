import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Departamento } from './departamento.model';
import { DepartamentoPopupService } from './departamento-popup.service';
import { DepartamentoService } from './departamento.service';

@Component({
    selector: 'jhi-departamento-delete-dialog',
    templateUrl: './departamento-delete-dialog.component.html'
})
export class DepartamentoDeleteDialogComponent {

    departamento: Departamento;

    constructor(
        private departamentoService: DepartamentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.departamentoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'departamentoListModification',
                content: 'Deleted an departamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-departamento-delete-popup',
    template: ''
})
export class DepartamentoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private departamentoPopupService: DepartamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.departamentoPopupService
                .open(DepartamentoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
