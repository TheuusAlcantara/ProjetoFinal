import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DepartamentoComponent } from './departamento.component';
import { DepartamentoDetailComponent } from './departamento-detail.component';
import { DepartamentoPopupComponent } from './departamento-dialog.component';
import { DepartamentoDeletePopupComponent } from './departamento-delete-dialog.component';

@Injectable()
export class DepartamentoResolvePagingParams implements Resolve<any> {

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

export const departamentoRoute: Routes = [
    {
        path: 'departamento',
        component: DepartamentoComponent,
        resolve: {
            'pagingParams': DepartamentoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departamentos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'departamento/:id',
        component: DepartamentoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departamentos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const departamentoPopupRoute: Routes = [
    {
        path: 'departamento-new',
        component: DepartamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'departamento/:id/edit',
        component: DepartamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'departamento/:id/delete',
        component: DepartamentoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Departamentos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
