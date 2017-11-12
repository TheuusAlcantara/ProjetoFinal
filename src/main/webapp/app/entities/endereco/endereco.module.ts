import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaafaSharedModule } from '../../shared';
import {
    EnderecoService,
    EnderecoPopupService,
    EnderecoComponent,
    EnderecoDetailComponent,
    EnderecoDialogComponent,
    EnderecoPopupComponent,
    EnderecoDeletePopupComponent,
    EnderecoDeleteDialogComponent,
    enderecoRoute,
    enderecoPopupRoute,
    EnderecoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...enderecoRoute,
    ...enderecoPopupRoute,
];

@NgModule({
    imports: [
        SaafaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EnderecoComponent,
        EnderecoDetailComponent,
        EnderecoDialogComponent,
        EnderecoDeleteDialogComponent,
        EnderecoPopupComponent,
        EnderecoDeletePopupComponent,
    ],
    entryComponents: [
        EnderecoComponent,
        EnderecoDialogComponent,
        EnderecoPopupComponent,
        EnderecoDeleteDialogComponent,
        EnderecoDeletePopupComponent,
    ],
    providers: [
        EnderecoService,
        EnderecoPopupService,
        EnderecoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaEnderecoModule {}
