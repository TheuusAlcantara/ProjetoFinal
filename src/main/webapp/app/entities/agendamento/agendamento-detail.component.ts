import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Agendamento } from './agendamento.model';
import { AgendamentoService } from './agendamento.service';

@Component({
    selector: 'jhi-agendamento-detail',
    templateUrl: './agendamento-detail.component.html'
})
export class AgendamentoDetailComponent implements OnInit, OnDestroy {

    agendamento: Agendamento;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private agendamentoService: AgendamentoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgendamentos();
    }

    load(id) {
        this.agendamentoService.find(id).subscribe((agendamento) => {
            this.agendamento = agendamento;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAgendamentos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agendamentoListModification',
            (response) => this.load(this.agendamento.id)
        );
    }
}
