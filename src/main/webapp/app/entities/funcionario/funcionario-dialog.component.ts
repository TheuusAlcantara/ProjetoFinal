import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Funcionario } from './funcionario.model';
import { FuncionarioPopupService } from './funcionario-popup.service';
import { FuncionarioService } from './funcionario.service';
import { Endereco, EnderecoService } from '../endereco';
import { Cargo, CargoService } from '../cargo';
import { Expediente, ExpedienteService } from '../expediente';
import { FluxoAtendimento, FluxoAtendimentoService } from '../fluxo-atendimento';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-funcionario-dialog',
    templateUrl: './funcionario-dialog.component.html'
})
export class FuncionarioDialogComponent implements OnInit {

    funcionario: Funcionario;
    isSaving: boolean;

    enderecos: Endereco[];

    cargos: Cargo[];

    expedientes: Expediente[];

    fluxoatendimentos: FluxoAtendimento[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private funcionarioService: FuncionarioService,
        private enderecoService: EnderecoService,
        private cargoService: CargoService,
        private expedienteService: ExpedienteService,
        private fluxoAtendimentoService: FluxoAtendimentoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.enderecoService
            .query({filter: 'funcionario-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.funcionario.enderecoId) {
                    this.enderecos = res.json;
                } else {
                    this.enderecoService
                        .find(this.funcionario.enderecoId)
                        .subscribe((subRes: Endereco) => {
                            this.enderecos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.cargoService
            .query({filter: 'funcionario-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.funcionario.cargoId) {
                    this.cargos = res.json;
                } else {
                    this.cargoService
                        .find(this.funcionario.cargoId)
                        .subscribe((subRes: Cargo) => {
                            this.cargos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.expedienteService.query()
            .subscribe((res: ResponseWrapper) => { this.expedientes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.fluxoAtendimentoService.query()
            .subscribe((res: ResponseWrapper) => { this.fluxoatendimentos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.funcionario.id !== undefined) {
            this.subscribeToSaveResponse(
                this.funcionarioService.update(this.funcionario));
        } else {
            this.subscribeToSaveResponse(
                this.funcionarioService.create(this.funcionario));
        }
    }

    private subscribeToSaveResponse(result: Observable<Funcionario>) {
        result.subscribe((res: Funcionario) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Funcionario) {
        this.eventManager.broadcast({ name: 'funcionarioListModification', content: 'OK'});
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

    trackCargoById(index: number, item: Cargo) {
        return item.id;
    }

    trackExpedienteById(index: number, item: Expediente) {
        return item.id;
    }

    trackFluxoAtendimentoById(index: number, item: FluxoAtendimento) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-funcionario-popup',
    template: ''
})
export class FuncionarioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private funcionarioPopupService: FuncionarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.funcionarioPopupService
                    .open(FuncionarioDialogComponent as Component, params['id']);
            } else {
                this.funcionarioPopupService
                    .open(FuncionarioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
