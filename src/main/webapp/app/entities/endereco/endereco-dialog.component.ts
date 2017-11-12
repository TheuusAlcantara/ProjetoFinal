import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Endereco } from './endereco.model';
import { EnderecoPopupService } from './endereco-popup.service';
import { EnderecoService } from './endereco.service';
import { Cidade, CidadeService } from '../cidade';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-endereco-dialog',
    templateUrl: './endereco-dialog.component.html'
})
export class EnderecoDialogComponent implements OnInit {

    endereco: Endereco;
    isSaving: boolean;

    cidades: Cidade[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private enderecoService: EnderecoService,
        private cidadeService: CidadeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cidadeService.query()
            .subscribe((res: ResponseWrapper) => { this.cidades = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.endereco.id !== undefined) {
            this.subscribeToSaveResponse(
                this.enderecoService.update(this.endereco));
        } else {
            this.subscribeToSaveResponse(
                this.enderecoService.create(this.endereco));
        }
    }

    private subscribeToSaveResponse(result: Observable<Endereco>) {
        result.subscribe((res: Endereco) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Endereco) {
        this.eventManager.broadcast({ name: 'enderecoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCidadeById(index: number, item: Cidade) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-endereco-popup',
    template: ''
})
export class EnderecoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enderecoPopupService: EnderecoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.enderecoPopupService
                    .open(EnderecoDialogComponent as Component, params['id']);
            } else {
                this.enderecoPopupService
                    .open(EnderecoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
