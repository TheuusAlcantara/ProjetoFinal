import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaafaSharedModule } from '../../shared';
import {
    DepartamentoService,
    DepartamentoPopupService,
    DepartamentoComponent,
    DepartamentoDetailComponent,
    DepartamentoDialogComponent,
    DepartamentoPopupComponent,
    DepartamentoDeletePopupComponent,
    DepartamentoDeleteDialogComponent,
    departamentoRoute,
    departamentoPopupRoute,
    DepartamentoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...departamentoRoute,
    ...departamentoPopupRoute,
];

@NgModule({
    imports: [
        SaafaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DepartamentoComponent,
        DepartamentoDetailComponent,
        DepartamentoDialogComponent,
        DepartamentoDeleteDialogComponent,
        DepartamentoPopupComponent,
        DepartamentoDeletePopupComponent,
    ],
    entryComponents: [
        DepartamentoComponent,
        DepartamentoDialogComponent,
        DepartamentoPopupComponent,
        DepartamentoDeleteDialogComponent,
        DepartamentoDeletePopupComponent,
    ],
    providers: [
        DepartamentoService,
        DepartamentoPopupService,
        DepartamentoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaDepartamentoModule {}
