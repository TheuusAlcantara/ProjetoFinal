import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CidadeComponent } from './cidade.component';
import { CidadeDetailComponent } from './cidade-detail.component';
import { CidadePopupComponent } from './cidade-dialog.component';
import { CidadeDeletePopupComponent } from './cidade-delete-dialog.component';

@Injectable()
export class CidadeResolvePagingParams implements Resolve<any> {

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

export const cidadeRoute: Routes = [
    {
        path: 'cidade',
        component: CidadeComponent,
        resolve: {
            'pagingParams': CidadeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cidade/:id',
        component: CidadeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cidadePopupRoute: Routes = [
    {
        path: 'cidade-new',
        component: CidadePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cidade/:id/edit',
        component: CidadePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cidade/:id/delete',
        component: CidadeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Cidades'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
