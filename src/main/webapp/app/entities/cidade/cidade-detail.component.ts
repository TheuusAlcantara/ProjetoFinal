import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Cidade } from './cidade.model';
import { CidadeService } from './cidade.service';

@Component({
    selector: 'jhi-cidade-detail',
    templateUrl: './cidade-detail.component.html'
})
export class CidadeDetailComponent implements OnInit, OnDestroy {

    cidade: Cidade;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cidadeService: CidadeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCidades();
    }

    load(id) {
        this.cidadeService.find(id).subscribe((cidade) => {
            this.cidade = cidade;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCidades() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cidadeListModification',
            (response) => this.load(this.cidade.id)
        );
    }
}
