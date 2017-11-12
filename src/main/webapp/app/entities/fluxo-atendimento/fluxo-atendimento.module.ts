import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaafaSharedModule } from '../../shared';
import {
    FluxoAtendimentoService,
    FluxoAtendimentoPopupService,
    FluxoAtendimentoComponent,
    FluxoAtendimentoDetailComponent,
    FluxoAtendimentoDialogComponent,
    FluxoAtendimentoPopupComponent,
    FluxoAtendimentoDeletePopupComponent,
    FluxoAtendimentoDeleteDialogComponent,
    fluxoAtendimentoRoute,
    fluxoAtendimentoPopupRoute,
    FluxoAtendimentoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fluxoAtendimentoRoute,
    ...fluxoAtendimentoPopupRoute,
];

@NgModule({
    imports: [
        SaafaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FluxoAtendimentoComponent,
        FluxoAtendimentoDetailComponent,
        FluxoAtendimentoDialogComponent,
        FluxoAtendimentoDeleteDialogComponent,
        FluxoAtendimentoPopupComponent,
        FluxoAtendimentoDeletePopupComponent,
    ],
    entryComponents: [
        FluxoAtendimentoComponent,
        FluxoAtendimentoDialogComponent,
        FluxoAtendimentoPopupComponent,
        FluxoAtendimentoDeleteDialogComponent,
        FluxoAtendimentoDeletePopupComponent,
    ],
    providers: [
        FluxoAtendimentoService,
        FluxoAtendimentoPopupService,
        FluxoAtendimentoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaFluxoAtendimentoModule {}
