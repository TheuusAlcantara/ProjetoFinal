import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FuncionarioComponent } from './funcionario.component';
import { FuncionarioDetailComponent } from './funcionario-detail.component';
import { FuncionarioPopupComponent } from './funcionario-dialog.component';
import { FuncionarioDeletePopupComponent } from './funcionario-delete-dialog.component';

@Injectable()
export class FuncionarioResolvePagingParams implements Resolve<any> {

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

export const funcionarioRoute: Routes = [
    {
        path: 'funcionario',
        component: FuncionarioComponent,
        resolve: {
            'pagingParams': FuncionarioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Funcionarios'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'funcionario/:id',
        component: FuncionarioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Funcionarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const funcionarioPopupRoute: Routes = [
    {
        path: 'funcionario-new',
        component: FuncionarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Funcionarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'funcionario/:id/edit',
        component: FuncionarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Funcionarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'funcionario/:id/delete',
        component: FuncionarioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Funcionarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
