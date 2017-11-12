import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExpedienteComponent } from './expediente.component';
import { ExpedienteDetailComponent } from './expediente-detail.component';
import { ExpedientePopupComponent } from './expediente-dialog.component';
import { ExpedienteDeletePopupComponent } from './expediente-delete-dialog.component';

@Injectable()
export class ExpedienteResolvePagingParams implements Resolve<any> {

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

export const expedienteRoute: Routes = [
    {
        path: 'expediente',
        component: ExpedienteComponent,
        resolve: {
            'pagingParams': ExpedienteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Expedientes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'expediente/:id',
        component: ExpedienteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Expedientes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const expedientePopupRoute: Routes = [
    {
        path: 'expediente-new',
        component: ExpedientePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Expedientes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'expediente/:id/edit',
        component: ExpedientePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Expedientes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'expediente/:id/delete',
        component: ExpedienteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Expedientes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
