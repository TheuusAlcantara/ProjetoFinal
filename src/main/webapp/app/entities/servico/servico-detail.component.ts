import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Servico } from './servico.model';
import { ServicoService } from './servico.service';

@Component({
    selector: 'jhi-servico-detail',
    templateUrl: './servico-detail.component.html'
})
export class ServicoDetailComponent implements OnInit, OnDestroy {

    servico: Servico;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private servicoService: ServicoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServicos();
    }

    load(id) {
        this.servicoService.find(id).subscribe((servico) => {
            this.servico = servico;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInServicos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'servicoListModification',
            (response) => this.load(this.servico.id)
        );
    }
}
