import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaafaSharedModule } from '../../shared';
import {
    AgendamentoService,
    AgendamentoPopupService,
    AgendamentoComponent,
    AgendamentoDetailComponent,
    AgendamentoDialogComponent,
    AgendamentoPopupComponent,
    AgendamentoDeletePopupComponent,
    AgendamentoDeleteDialogComponent,
    agendamentoRoute,
    agendamentoPopupRoute,
    AgendamentoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...agendamentoRoute,
    ...agendamentoPopupRoute,
];

@NgModule({
    imports: [
        SaafaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AgendamentoComponent,
        AgendamentoDetailComponent,
        AgendamentoDialogComponent,
        AgendamentoDeleteDialogComponent,
        AgendamentoPopupComponent,
        AgendamentoDeletePopupComponent,
    ],
    entryComponents: [
        AgendamentoComponent,
        AgendamentoDialogComponent,
        AgendamentoPopupComponent,
        AgendamentoDeleteDialogComponent,
        AgendamentoDeletePopupComponent,
    ],
    providers: [
        AgendamentoService,
        AgendamentoPopupService,
        AgendamentoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaAgendamentoModule {}
