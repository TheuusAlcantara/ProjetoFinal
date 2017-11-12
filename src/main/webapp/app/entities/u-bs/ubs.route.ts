import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UBSComponent } from './ubs.component';
import { UBSDetailComponent } from './ubs-detail.component';
import { UBSPopupComponent } from './ubs-dialog.component';
import { UBSDeletePopupComponent } from './ubs-delete-dialog.component';

@Injectable()
export class UBSResolvePagingParams implements Resolve<any> {

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

export const uBSRoute: Routes = [
    {
        path: 'ubs',
        component: UBSComponent,
        resolve: {
            'pagingParams': UBSResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UBS'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ubs/:id',
        component: UBSDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UBS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const uBSPopupRoute: Routes = [
    {
        path: 'ubs-new',
        component: UBSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UBS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ubs/:id/edit',
        component: UBSPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UBS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ubs/:id/delete',
        component: UBSDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UBS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
