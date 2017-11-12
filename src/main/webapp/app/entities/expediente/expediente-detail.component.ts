import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Expediente } from './expediente.model';
import { ExpedienteService } from './expediente.service';

@Component({
    selector: 'jhi-expediente-detail',
    templateUrl: './expediente-detail.component.html'
})
export class ExpedienteDetailComponent implements OnInit, OnDestroy {

    expediente: Expediente;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private expedienteService: ExpedienteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExpedientes();
    }

    load(id) {
        this.expedienteService.find(id).subscribe((expediente) => {
            this.expediente = expediente;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExpedientes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'expedienteListModification',
            (response) => this.load(this.expediente.id)
        );
    }
}
