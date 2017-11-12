import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Departamento } from './departamento.model';
import { DepartamentoPopupService } from './departamento-popup.service';
import { DepartamentoService } from './departamento.service';
import { Servico, ServicoService } from '../servico';
import { Funcionario, FuncionarioService } from '../funcionario';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-departamento-dialog',
    templateUrl: './departamento-dialog.component.html'
})
export class DepartamentoDialogComponent implements OnInit {

    departamento: Departamento;
    isSaving: boolean;

    servicos: Servico[];

    funcionarios: Funcionario[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private departamentoService: DepartamentoService,
        private servicoService: ServicoService,
        private funcionarioService: FuncionarioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.servicoService.query()
            .subscribe((res: ResponseWrapper) => { this.servicos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.funcionarioService.query()
            .subscribe((res: ResponseWrapper) => { this.funcionarios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.departamento.id !== undefined) {
            this.subscribeToSaveResponse(
                this.departamentoService.update(this.departamento));
        } else {
            this.subscribeToSaveResponse(
                this.departamentoService.create(this.departamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<Departamento>) {
        result.subscribe((res: Departamento) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Departamento) {
        this.eventManager.broadcast({ name: 'departamentoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackServicoById(index: number, item: Servico) {
        return item.id;
    }

    trackFuncionarioById(index: number, item: Funcionario) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-departamento-popup',
    template: ''
})
export class DepartamentoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private departamentoPopupService: DepartamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.departamentoPopupService
                    .open(DepartamentoDialogComponent as Component, params['id']);
            } else {
                this.departamentoPopupService
                    .open(DepartamentoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
