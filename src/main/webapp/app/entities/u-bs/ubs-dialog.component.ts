import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UBS } from './ubs.model';
import { UBSPopupService } from './ubs-popup.service';
import { UBSService } from './ubs.service';
import { Endereco, EnderecoService } from '../endereco';
import { Departamento, DepartamentoService } from '../departamento';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ubs-dialog',
    templateUrl: './ubs-dialog.component.html'
})
export class UBSDialogComponent implements OnInit {

    uBS: UBS;
    isSaving: boolean;

    enderecos: Endereco[];

    departamentos: Departamento[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private uBSService: UBSService,
        private enderecoService: EnderecoService,
        private departamentoService: DepartamentoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.enderecoService
            .query({filter: 'ubs-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.uBS.enderecoId) {
                    this.enderecos = res.json;
                } else {
                    this.enderecoService
                        .find(this.uBS.enderecoId)
                        .subscribe((subRes: Endereco) => {
                            this.enderecos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.departamentoService.query()
            .subscribe((res: ResponseWrapper) => { this.departamentos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.uBS.id !== undefined) {
            this.subscribeToSaveResponse(
                this.uBSService.update(this.uBS));
        } else {
            this.subscribeToSaveResponse(
                this.uBSService.create(this.uBS));
        }
    }

    private subscribeToSaveResponse(result: Observable<UBS>) {
        result.subscribe((res: UBS) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UBS) {
        this.eventManager.broadcast({ name: 'uBSListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEnderecoById(index: number, item: Endereco) {
        return item.id;
    }

    trackDepartamentoById(index: number, item: Departamento) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ubs-popup',
    template: ''
})
export class UBSPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uBSPopupService: UBSPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.uBSPopupService
                    .open(UBSDialogComponent as Component, params['id']);
            } else {
                this.uBSPopupService
                    .open(UBSDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
