import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Funcionario } from './funcionario.model';
import { FuncionarioPopupService } from './funcionario-popup.service';
import { FuncionarioService } from './funcionario.service';

@Component({
    selector: 'jhi-funcionario-delete-dialog',
    templateUrl: './funcionario-delete-dialog.component.html'
})
export class FuncionarioDeleteDialogComponent {

    funcionario: Funcionario;

    constructor(
        private funcionarioService: FuncionarioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.funcionarioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'funcionarioListModification',
                content: 'Deleted an funcionario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-funcionario-delete-popup',
    template: ''
})
export class FuncionarioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private funcionarioPopupService: FuncionarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.funcionarioPopupService
                .open(FuncionarioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
