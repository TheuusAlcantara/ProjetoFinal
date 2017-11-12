import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ServicoComponent } from './servico.component';
import { ServicoDetailComponent } from './servico-detail.component';
import { ServicoPopupComponent } from './servico-dialog.component';
import { ServicoDeletePopupComponent } from './servico-delete-dialog.component';

@Injectable()
export class ServicoResolvePagingParams implements Resolve<any> {

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

export const servicoRoute: Routes = [
    {
        path: 'servico',
        component: ServicoComponent,
        resolve: {
            'pagingParams': ServicoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servicos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'servico/:id',
        component: ServicoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servicos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servicoPopupRoute: Routes = [
    {
        path: 'servico-new',
        component: ServicoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servicos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'servico/:id/edit',
        component: ServicoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servicos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'servico/:id/delete',
        component: ServicoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Servicos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
