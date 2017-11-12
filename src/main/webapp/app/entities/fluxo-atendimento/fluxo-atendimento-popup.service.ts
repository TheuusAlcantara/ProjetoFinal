import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FluxoAtendimento } from './fluxo-atendimento.model';
import { FluxoAtendimentoService } from './fluxo-atendimento.service';

@Injectable()
export class FluxoAtendimentoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private fluxoAtendimentoService: FluxoAtendimentoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.fluxoAtendimentoService.find(id).subscribe((fluxoAtendimento) => {
                    this.ngbModalRef = this.fluxoAtendimentoModalRef(component, fluxoAtendimento);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.fluxoAtendimentoModalRef(component, new FluxoAtendimento());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    fluxoAtendimentoModalRef(component: Component, fluxoAtendimento: FluxoAtendimento): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fluxoAtendimento = fluxoAtendimento;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
