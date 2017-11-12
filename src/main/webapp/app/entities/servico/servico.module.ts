import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaafaSharedModule } from '../../shared';
import {
    ServicoService,
    ServicoPopupService,
    ServicoComponent,
    ServicoDetailComponent,
    ServicoDialogComponent,
    ServicoPopupComponent,
    ServicoDeletePopupComponent,
    ServicoDeleteDialogComponent,
    servicoRoute,
    servicoPopupRoute,
    ServicoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...servicoRoute,
    ...servicoPopupRoute,
];

@NgModule({
    imports: [
        SaafaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ServicoComponent,
        ServicoDetailComponent,
        ServicoDialogComponent,
        ServicoDeleteDialogComponent,
        ServicoPopupComponent,
        ServicoDeletePopupComponent,
    ],
    entryComponents: [
        ServicoComponent,
        ServicoDialogComponent,
        ServicoPopupComponent,
        ServicoDeleteDialogComponent,
        ServicoDeletePopupComponent,
    ],
    providers: [
        ServicoService,
        ServicoPopupService,
        ServicoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaServicoModule {}
