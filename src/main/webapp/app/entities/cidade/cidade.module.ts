import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaafaSharedModule } from '../../shared';
import {
    CidadeService,
    CidadePopupService,
    CidadeComponent,
    CidadeDetailComponent,
    CidadeDialogComponent,
    CidadePopupComponent,
    CidadeDeletePopupComponent,
    CidadeDeleteDialogComponent,
    cidadeRoute,
    cidadePopupRoute,
    CidadeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cidadeRoute,
    ...cidadePopupRoute,
];

@NgModule({
    imports: [
        SaafaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CidadeComponent,
        CidadeDetailComponent,
        CidadeDialogComponent,
        CidadeDeleteDialogComponent,
        CidadePopupComponent,
        CidadeDeletePopupComponent,
    ],
    entryComponents: [
        CidadeComponent,
        CidadeDialogComponent,
        CidadePopupComponent,
        CidadeDeleteDialogComponent,
        CidadeDeletePopupComponent,
    ],
    providers: [
        CidadeService,
        CidadePopupService,
        CidadeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaCidadeModule {}
