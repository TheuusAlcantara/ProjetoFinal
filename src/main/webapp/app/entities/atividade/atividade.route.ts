import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AtividadeComponent } from './atividade.component';
import { AtividadeDetailComponent } from './atividade-detail.component';
import { AtividadePopupComponent } from './atividade-dialog.component';
import { AtividadeDeletePopupComponent } from './atividade-delete-dialog.component';

@Injectable()
export class AtividadeResolvePagingParams implements Resolve<any> {

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

export const atividadeRoute: Routes = [
    {
        path: 'atividade',
        component: AtividadeComponent,
        resolve: {
            'pagingParams': AtividadeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Atividades'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'atividade/:id',
        component: AtividadeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Atividades'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const atividadePopupRoute: Routes = [
    {
        path: 'atividade-new',
        component: AtividadePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Atividades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'atividade/:id/edit',
        component: AtividadePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Atividades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'atividade/:id/delete',
        component: AtividadeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Atividades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
