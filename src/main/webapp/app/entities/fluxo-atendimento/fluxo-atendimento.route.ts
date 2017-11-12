import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FluxoAtendimentoComponent } from './fluxo-atendimento.component';
import { FluxoAtendimentoDetailComponent } from './fluxo-atendimento-detail.component';
import { FluxoAtendimentoPopupComponent } from './fluxo-atendimento-dialog.component';
import { FluxoAtendimentoDeletePopupComponent } from './fluxo-atendimento-delete-dialog.component';

@Injectable()
export class FluxoAtendimentoResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const fluxoAtendimentoRoute: Routes = [
    {
        path: 'fluxo-atendimento',
        component: FluxoAtendimentoComponent,
        resolve: {
            'pagingParams': FluxoAtendimentoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FluxoAtendimentos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fluxo-atendimento/:id',
        component: FluxoAtendimentoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FluxoAtendimentos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fluxoAtendimentoPopupRoute: Routes = [
    {
        path: 'fluxo-atendimento-new',
        component: FluxoAtendimentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FluxoAtendimentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fluxo-atendimento/:id/edit',
        component: FluxoAtendimentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FluxoAtendimentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fluxo-atendimento/:id/delete',
        component: FluxoAtendimentoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FluxoAtendimentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
