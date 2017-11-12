import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cidade } from './cidade.model';
import { CidadePopupService } from './cidade-popup.service';
import { CidadeService } from './cidade.service';

@Component({
    selector: 'jhi-cidade-dialog',
    templateUrl: './cidade-dialog.component.html'
})
export class CidadeDialogComponent implements OnInit {

    cidade: Cidade;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cidadeService: CidadeService,
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
        if (this.cidade.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cidadeService.update(this.cidade));
        } else {
            this.subscribeToSaveResponse(
                this.cidadeService.create(this.cidade));
        }
    }

    private subscribeToSaveResponse(result: Observable<Cidade>) {
        result.subscribe((res: Cidade) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Cidade) {
        this.eventManager.broadcast({ name: 'cidadeListModification', content: 'OK'});
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
    selector: 'jhi-cidade-popup',
    template: ''
})
export class CidadePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cidadePopupService: CidadePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cidadePopupService
                    .open(CidadeDialogComponent as Component, params['id']);
            } else {
                this.cidadePopupService
                    .open(CidadeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
