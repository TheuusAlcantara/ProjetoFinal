import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cidade } from './cidade.model';
import { CidadePopupService } from './cidade-popup.service';
import { CidadeService } from './cidade.service';

@Component({
    selector: 'jhi-cidade-delete-dialog',
    templateUrl: './cidade-delete-dialog.component.html'
})
export class CidadeDeleteDialogComponent {

    cidade: Cidade;

    constructor(
        private cidadeService: CidadeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cidadeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cidadeListModification',
                content: 'Deleted an cidade'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cidade-delete-popup',
    template: ''
})
export class CidadeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cidadePopupService: CidadePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cidadePopupService
                .open(CidadeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
