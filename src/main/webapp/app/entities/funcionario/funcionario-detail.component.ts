import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Funcionario } from './funcionario.model';
import { FuncionarioService } from './funcionario.service';

@Component({
    selector: 'jhi-funcionario-detail',
    templateUrl: './funcionario-detail.component.html'
})
export class FuncionarioDetailComponent implements OnInit, OnDestroy {

    funcionario: Funcionario;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private funcionarioService: FuncionarioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFuncionarios();
    }

    load(id) {
        this.funcionarioService.find(id).subscribe((funcionario) => {
            this.funcionario = funcionario;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFuncionarios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'funcionarioListModification',
            (response) => this.load(this.funcionario.id)
        );
    }
}
