import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cargo } from './cargo.model';
import { CargoPopupService } from './cargo-popup.service';
import { CargoService } from './cargo.service';

@Component({
    selector: 'jhi-cargo-dialog',
    templateUrl: './cargo-dialog.component.html'
})
export class CargoDialogComponent implements OnInit {

    cargo: Cargo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cargoService: CargoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cargo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cargoService.update(this.cargo));
        } else {
            this.subscribeToSaveResponse(
                this.cargoService.create(this.cargo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Cargo>) {
        result.subscribe((res: Cargo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Cargo) {
        this.eventManager.broadcast({ name: 'cargoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-cargo-popup',
    template: ''
})
export class CargoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cargoPopupService: CargoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cargoPopupService
                    .open(CargoDialogComponent as Component, params['id']);
            } else {
                this.cargoPopupService
                    .open(CargoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
