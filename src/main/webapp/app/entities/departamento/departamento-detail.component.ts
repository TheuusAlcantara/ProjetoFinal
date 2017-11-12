import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Departamento } from './departamento.model';
import { DepartamentoService } from './departamento.service';

@Component({
    selector: 'jhi-departamento-detail',
    templateUrl: './departamento-detail.component.html'
})
export class DepartamentoDetailComponent implements OnInit, OnDestroy {

    departamento: Departamento;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private departamentoService: DepartamentoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDepartamentos();
    }

    load(id) {
        this.departamentoService.find(id).subscribe((departamento) => {
            this.departamento = departamento;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDepartamentos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'departamentoListModification',
            (response) => this.load(this.departamento.id)
        );
    }
}
