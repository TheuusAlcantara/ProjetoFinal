import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AgendamentoComponent } from './agendamento.component';
import { AgendamentoDetailComponent } from './agendamento-detail.component';
import { AgendamentoPopupComponent } from './agendamento-dialog.component';
import { AgendamentoDeletePopupComponent } from './agendamento-delete-dialog.component';

@Injectable()
export class AgendamentoResolvePagingParams implements Resolve<any> {

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

export const agendamentoRoute: Routes = [
    {
        path: 'agendamento',
        component: AgendamentoComponent,
        resolve: {
            'pagingParams': AgendamentoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Agendamentos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agendamento/:id',
        component: AgendamentoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Agendamentos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agendamentoPopupRoute: Routes = [
    {
        path: 'agendamento-new',
        component: AgendamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Agendamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agendamento/:id/edit',
        component: AgendamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Agendamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agendamento/:id/delete',
        component: AgendamentoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Agendamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
