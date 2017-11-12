import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FluxoAtendimento } from './fluxo-atendimento.model';
import { FluxoAtendimentoPopupService } from './fluxo-atendimento-popup.service';
import { FluxoAtendimentoService } from './fluxo-atendimento.service';
import { Agendamento, AgendamentoService } from '../agendamento';
import { UBS, UBSService } from '../u-bs';
import { Funcionario, FuncionarioService } from '../funcionario';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-fluxo-atendimento-dialog',
    templateUrl: './fluxo-atendimento-dialog.component.html'
})
export class FluxoAtendimentoDialogComponent implements OnInit {

    fluxoAtendimento: FluxoAtendimento;
    isSaving: boolean;

    agendamentos: Agendamento[];

    ubs: UBS[];

    funcionarios: Funcionario[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private fluxoAtendimentoService: FluxoAtendimentoService,
        private agendamentoService: AgendamentoService,
        private uBSService: UBSService,
        private funcionarioService: FuncionarioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.agendamentoService
            .query({filter: 'fluxoatendimento-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.fluxoAtendimento.agendamentoId) {
                    this.agendamentos = res.json;
                } else {
                    this.agendamentoService
                        .find(this.fluxoAtendimento.agendamentoId)
                        .subscribe((subRes: Agendamento) => {
                            this.agendamentos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.uBSService
            .query({filter: 'fluxoatendimento-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.fluxoAtendimento.ubsId) {
                    this.ubs = res.json;
                } else {
                    this.uBSService
                        .find(this.fluxoAtendimento.ubsId)
                        .subscribe((subRes: UBS) => {
                            this.ubs = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.funcionarioService.query()
            .subscribe((res: ResponseWrapper) => { this.funcionarios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fluxoAtendimento.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fluxoAtendimentoService.update(this.fluxoAtendimento));
        } else {
            this.subscribeToSaveResponse(
                this.fluxoAtendimentoService.create(this.fluxoAtendimento));
        }
    }

    private subscribeToSaveResponse(result: Observable<FluxoAtendimento>) {
        result.subscribe((res: FluxoAtendimento) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FluxoAtendimento) {
        this.eventManager.broadcast({ name: 'fluxoAtendimentoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAgendamentoById(index: number, item: Agendamento) {
        return item.id;
    }

    trackUBSById(index: number, item: UBS) {
        return item.id;
    }

    trackFuncionarioById(index: number, item: Funcionario) {
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
    selector: 'jhi-fluxo-atendimento-popup',
    template: ''
})
export class FluxoAtendimentoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fluxoAtendimentoPopupService: FluxoAtendimentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fluxoAtendimentoPopupService
                    .open(FluxoAtendimentoDialogComponent as Component, params['id']);
            } else {
                this.fluxoAtendimentoPopupService
                    .open(FluxoAtendimentoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
