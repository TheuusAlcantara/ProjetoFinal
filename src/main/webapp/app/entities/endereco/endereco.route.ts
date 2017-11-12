import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EnderecoComponent } from './endereco.component';
import { EnderecoDetailComponent } from './endereco-detail.component';
import { EnderecoPopupComponent } from './endereco-dialog.component';
import { EnderecoDeletePopupComponent } from './endereco-delete-dialog.component';

@Injectable()
export class EnderecoResolvePagingParams implements Resolve<any> {

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

export const enderecoRoute: Routes = [
    {
        path: 'endereco',
        component: EnderecoComponent,
        resolve: {
            'pagingParams': EnderecoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enderecos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'endereco/:id',
        component: EnderecoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enderecos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enderecoPopupRoute: Routes = [
    {
        path: 'endereco-new',
        component: EnderecoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enderecos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'endereco/:id/edit',
        component: EnderecoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enderecos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'endereco/:id/delete',
        component: EnderecoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Enderecos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
