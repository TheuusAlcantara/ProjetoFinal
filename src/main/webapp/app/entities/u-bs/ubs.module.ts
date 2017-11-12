import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SaafaSharedModule } from '../../shared';
import {
    UBSService,
    UBSPopupService,
    UBSComponent,
    UBSDetailComponent,
    UBSDialogComponent,
    UBSPopupComponent,
    UBSDeletePopupComponent,
    UBSDeleteDialogComponent,
    uBSRoute,
    uBSPopupRoute,
    UBSResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...uBSRoute,
    ...uBSPopupRoute,
];

@NgModule({
    imports: [
        SaafaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UBSComponent,
        UBSDetailComponent,
        UBSDialogComponent,
        UBSDeleteDialogComponent,
        UBSPopupComponent,
        UBSDeletePopupComponent,
    ],
    entryComponents: [
        UBSComponent,
        UBSDialogComponent,
        UBSPopupComponent,
        UBSDeleteDialogComponent,
        UBSDeletePopupComponent,
    ],
    providers: [
        UBSService,
        UBSPopupService,
        UBSResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SaafaUBSModule {}
