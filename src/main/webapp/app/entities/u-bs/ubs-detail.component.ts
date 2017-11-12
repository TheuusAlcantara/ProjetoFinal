import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UBS } from './ubs.model';
import { UBSService } from './ubs.service';

@Component({
    selector: 'jhi-ubs-detail',
    templateUrl: './ubs-detail.component.html'
})
export class UBSDetailComponent implements OnInit, OnDestroy {

    uBS: UBS;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private uBSService: UBSService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUBS();
    }

    load(id) {
        this.uBSService.find(id).subscribe((uBS) => {
            this.uBS = uBS;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUBS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'uBSListModification',
            (response) => this.load(this.uBS.id)
        );
    }
}
