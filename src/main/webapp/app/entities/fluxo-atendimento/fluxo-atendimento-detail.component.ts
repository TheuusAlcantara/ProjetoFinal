import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FluxoAtendimento } from './fluxo-atendimento.model';
import { FluxoAtendimentoService } from './fluxo-atendimento.service';

@Component({
    selector: 'jhi-fluxo-atendimento-detail',
    templateUrl: './fluxo-atendimento-detail.component.html'
})
export class FluxoAtendimentoDetailComponent implements OnInit, OnDestroy {

    fluxoAtendimento: FluxoAtendimento;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fluxoAtendimentoService: FluxoAtendimentoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFluxoAtendimentos();
    }

    load(id) {
        this.fluxoAtendimentoService.find(id).subscribe((fluxoAtendimento) => {
            this.fluxoAtendimento = fluxoAtendimento;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFluxoAtendimentos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fluxoAtendimentoListModification',
            (response) => this.load(this.fluxoAtendimento.id)
        );
    }
}
